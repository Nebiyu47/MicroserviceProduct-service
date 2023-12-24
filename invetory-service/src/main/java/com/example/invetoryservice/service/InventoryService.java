package com.example.invetoryservice.service;

import com.example.invetoryservice.dto.InventoryResponse;
import com.example.invetoryservice.repository.InvetoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InvetoryRepository invetoryRepository;
    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCodes){
        log.info("wait time");
        Thread.sleep(1000);
        log.info("started");
        return invetoryRepository.findBySkuCodeIn(skuCodes).stream().map(inventory ->
            InventoryResponse.builder().skuCode(inventory.getSkuCode()).isInstock(inventory.getQuantity()>0).build()).toList();
    }
}

