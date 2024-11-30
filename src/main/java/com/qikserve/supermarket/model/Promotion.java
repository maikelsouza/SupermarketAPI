package com.qikserve.supermarket.model;

import com.qikserve.supermarket.enuns.TypePromotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Promotion {

    private String id;

    private Integer amount;

    private Integer requiredQty;

    private Integer freeQty;

    private  Double price;

    private TypePromotion typePromotion;

}
