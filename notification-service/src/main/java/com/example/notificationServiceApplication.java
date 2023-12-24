package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class notificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(notificationServiceApplication.class,args);
    }
    @KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlaceEvent orderPlacedEvent){
     log.info("Recived notifcation - {}",orderPlacedEvent.getOrderNumber());

    }

}
