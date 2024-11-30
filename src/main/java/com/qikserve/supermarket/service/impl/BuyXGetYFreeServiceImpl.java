package com.qikserve.supermarket.service.impl;

import com.qikserve.supermarket.model.Product;
import com.qikserve.supermarket.model.Promotion;
import com.qikserve.supermarket.service.PromotionService;
import org.springframework.stereotype.Service;

@Service
public class BuyXGetYFreeServiceImpl implements PromotionService {

    @Override
    public double applyDiscount(Product product, Promotion promotion, int quantity) {

        if (quantity >= promotion.getRequiredQty()) {
            int charged = (quantity + 1) / 2; // Round up
            return charged * product.getPrice();
        }
        return product.getPrice() * quantity;
    }
}
