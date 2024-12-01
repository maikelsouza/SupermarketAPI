package com.qikserve.supermarket.service.impl;

import com.qikserve.supermarket.model.Product;
import com.qikserve.supermarket.model.Promotion;
import com.qikserve.supermarket.service.PromotionService;
import org.springframework.stereotype.Service;

@Service
public class FlatPercentServiceImpl implements PromotionService {
    @Override
    public double applyDiscount(Product product, Promotion promotion, int quantity) {

        double priceWithDiscount = product.getPrice() * (1 - (double) promotion.getAmount() / 100);
        return quantity * priceWithDiscount;

    }
}
