package com.example.productservice.model;

import lombok.*;


import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="product")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String descreption;
    private BigDecimal price;

}
