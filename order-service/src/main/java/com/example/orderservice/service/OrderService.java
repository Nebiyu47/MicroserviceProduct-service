package com.example.orderservice.service;


import com.example.orderservice.Repository.OrderRepository;
import com.example.orderservice.dto.InventoryResponse;
import com.example.orderservice.dto.OrderLineItemDto;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.event.OrderPlaceEvent;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.OrderLineItems;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.ref.PhantomReference;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;
    private final Tracer tracer;
    private final KafkaTemplate<String,OrderPlaceEvent> kafkaTemplate;
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);
        List<String> skuCodes=order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode)
                .toList();
        log.info("calling the services");
       Span inventorylookup = tracer.nextSpan().name("InventoryServiceeLookup");
        try(Tracer.SpanInScope spanInScope =tracer.withSpan(inventorylookup.start())){
            InventoryResponse[] result =
                    webClient.build().get().uri("http://invetory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build()).retrieve().bodyToMono(InventoryResponse[].class).block();
            boolean allProductInStock= Arrays.stream(result).allMatch(InventoryResponse::isInstock);
            if (allProductInStock) {
                orderRepository.save(order);
                kafkaTemplate.send("notificationTopic",new OrderPlaceEvent(order.getOrderNumber()));
                return "order place sucessfuly";
            }else {


                throw new IllegalArgumentException("Product is not in Stock");
            }
            }finally {
            inventorylookup.end();
        }
    }
    private OrderLineItems mapToDto(OrderLineItemDto orderLineItemDto){
        OrderLineItems orderLineItems= new OrderLineItems();
        orderLineItems.setPrice(orderLineItemDto.getPrice());
        orderLineItems.setQuantity(orderLineItemDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemDto.getSkuCode());
        return orderLineItems;
    }
}
