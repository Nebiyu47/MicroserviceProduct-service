package com.example.invetoryservice;

import com.example.invetoryservice.model.Inventory;
import com.example.invetoryservice.repository.InvetoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InvetoryServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(InvetoryServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InvetoryRepository invetoryRepository) {
        return args -> {
            Inventory inventory = new Inventory();
            inventory.setSkuCode("iphone-13");
            inventory.setQuantity(1);

            Inventory inventory2 = new Inventory();
            inventory2.setSkuCode("iphone-13-red");
            inventory2.setQuantity(0);

            invetoryRepository.save(inventory);
            invetoryRepository.save(inventory2);


        };
    }
}


