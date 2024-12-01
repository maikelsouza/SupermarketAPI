package com.qikserve.supermarket.service;

import com.qikserve.supermarket.model.Basket;
import com.qikserve.supermarket.model.Product;
import org.antlr.v4.runtime.misc.NotNull;

public interface BasketService {


    Basket create();

    Basket addItem(Product product, Long id);

    Double calculateTotalCostApplyingPromotion(Long id);

    Double calculateTotalPromotion(Long id);
}
