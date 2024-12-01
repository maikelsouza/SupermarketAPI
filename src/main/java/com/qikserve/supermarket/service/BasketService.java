package com.qikserve.supermarket.service;

import com.qikserve.supermarket.model.Basket;
import com.qikserve.supermarket.model.Product;

public interface BasketService {


    Basket create();

    Basket addItem(Product product, Long id);

    Double calculateTotalCostApplyingPromotion(Long id);

    Double calculateTotalPromotion(Long id);
}
