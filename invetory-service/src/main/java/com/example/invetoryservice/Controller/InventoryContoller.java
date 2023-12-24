package com.example.invetoryservice.Controller;

import com.example.invetoryservice.dto.InventoryResponse;
import com.example.invetoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryContoller {
  private final InventoryService inventoryService;
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){

      return inventoryService.isInStock(skuCode);
    }
}
