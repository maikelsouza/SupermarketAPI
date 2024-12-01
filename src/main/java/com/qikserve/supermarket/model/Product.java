package com.qikserve.supermarket.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Product {

    private String id;

    private String name;

    private Double price;

    private List<Promotion> promotions;

}
