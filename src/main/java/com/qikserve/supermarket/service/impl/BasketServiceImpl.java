package com.qikserve.supermarket.service.impl;

import com.qikserve.supermarket.enuns.TypePromotion;
import com.qikserve.supermarket.model.Basket;
import com.qikserve.supermarket.model.Product;
import com.qikserve.supermarket.model.Promotion;
import com.qikserve.supermarket.service.BasketService;
import com.qikserve.supermarket.util.CalculationUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {

    private static final Logger logger = LogManager.getLogger(BasketServiceImpl.class);


    private final BuyXGetYFreeServiceImpl buyXGetYFreeServiceImpl;

    private final FlatPercentServiceImpl flatPercentServiceImpl;

    private final QtyBasedPriceOverrideServiceImpl qtyBasedPriceOverrideServiceImpl;

    private final List<Basket> baskets = new ArrayList<>();

    @Override
    public Basket create() {
        var basket = Basket.builder().id(1L).build();
        this.baskets.add(basket);
        logger.info("Basket created with ID: {}", basket.getId());
        return basket;
    }

    @Override
    public Basket addItem(Product product, Long id) {
        Optional<Basket> basket  = findBasketById(baskets, id);

        if (basket.isPresent()) {
            basket.get().addProduct(product);
            logger.info("Product {} add in Basket with ID {}", product.getId(), id);
        } else {
            logger.warn("Basket with ID {} not found.", id);
        }

        return basket.get();
    }

    public Double calculateTotalPromotion(Long id){
        double totalPromotion = CalculationUtil.roundToTwoDecimalPlaces(calculateTotalCostNoApplyPromotion(id) - calculateTotalCostApplyingPromotion(id));
        logger.debug("Total promotion calculated: {}", totalPromotion);
        return totalPromotion;
    }

    public Double calculateTotalCostNoApplyPromotion(Long id){
        Optional<Basket> basket = findBasketById(baskets, id);
        double totalCost = basket.map(b -> b.getProducts().stream().mapToDouble(Product::getPrice).sum()).orElse(0.0);
        logger.debug("Total without applying promotion for Basket ID {}: {}", id, totalCost);
        return CalculationUtil.roundToTwoDecimalPlaces(totalCost);
    }

    public Double calculateTotalCostApplyingPromotion(Long id){

        Optional<Basket> basket = findBasketById(baskets, id);

        if (basket.isEmpty()) {
            logger.warn("Basket with ID {} not found when calculating total cost with promotion.", id);
            return 0.0;
        }
        var products = basket.get().getProducts();
        double totalCost = 0.0;

        Set<String> productKeys = products
                .stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        Map<String, List<Product>> groupedByKey = products.stream()
                .collect(Collectors.groupingBy(Product::getId));


        for (String productKey: productKeys) {
            List<Product> productsGropuByKey = groupedByKey.get(productKey);
            int quantity = productsGropuByKey.size();
            Product product = productsGropuByKey.get(0);
            var promotions = product.getPromotions();
            if (promotions.isEmpty()){
                totalCost += product.getPrice() * quantity;
                logger.debug("No promotions for product {}. Total cost updated: {}", product.getId(), totalCost);

            }else{
                Promotion promotion = promotions.get(0);
                if (promotion.getTypePromotion() == TypePromotion.FLAT_PERCENT){
                    totalCost += flatPercentServiceImpl.applyDiscount(product,promotion, quantity);
                }
                if (promotion.getTypePromotion() == TypePromotion.BUY_X_GET_Y_FREE){
                    totalCost += buyXGetYFreeServiceImpl.applyDiscount(product,promotion, quantity);
                }
                if (promotion.getTypePromotion() == TypePromotion.QTY_BASED_PRICE_OVERRIDE){
                    totalCost += qtyBasedPriceOverrideServiceImpl.applyDiscount(product,promotion, quantity);
                }
                logger.debug("Total cost of promotions for the product {}: {}", product.getId(), totalCost);
            }
        }
        return CalculationUtil.roundToTwoDecimalPlaces(totalCost);
    }


    private static Optional<Basket> findBasketById(List<Basket> baskets, Long id) {
        return baskets.stream()
                .filter(basket -> basket.getId() == id)
                .findFirst();
    }

}