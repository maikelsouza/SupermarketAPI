package com.qikserve.supermarket.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("required_qty")
    private Integer requiredQty;

    @JsonProperty("free_qty")
    private Integer freeQty;

    private  Double price;

    @JsonProperty("type")
    private TypePromotion typePromotion;

}
