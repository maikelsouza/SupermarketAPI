package com.qikserve.supermarket.service;

import com.qikserve.supermarket.model.Product;
import com.qikserve.supermarket.model.Promotion;

public interface PromotionService {


    double applyDiscount(Product product, Promotion promotion, int quantity);
}
