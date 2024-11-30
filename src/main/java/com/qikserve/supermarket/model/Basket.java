package com.qikserve.supermarket.model;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Basket {

    private Long id;

    @Builder.Default
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product){
        this.products.add(product);
    }

    public Double getTotalCostNo(){
        return this.products.stream().mapToDouble(Product::getPrice).sum();
    }

}
