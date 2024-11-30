package com.qikserve.supermarket.service.impl;

import com.qikserve.supermarket.model.Product;
import com.qikserve.supermarket.model.Promotion;
import com.qikserve.supermarket.service.PromotionService;
import org.springframework.stereotype.Service;

@Service
public class QtyBasedPriceOverrideServiceImpl implements PromotionService {

    @Override
    public double applyDiscount(Product product, Promotion promotion, int quantity) {
        Integer requiredQty = promotion.getRequiredQty();
        if (quantity >= requiredQty) {
            return quantity * promotion.getPrice();
        }
        return product.getPrice() * quantity;
    }
}
