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
            int fullLots = quantity / requiredQty;
            int remainder = quantity % requiredQty;
            double discountedPrice = fullLots * promotion.getPrice();
            double regularPrice = remainder * product.getPrice();
            return discountedPrice + regularPrice;
        }
        return product.getPrice() * quantity;
    }
}
