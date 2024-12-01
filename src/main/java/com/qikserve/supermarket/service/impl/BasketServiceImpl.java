package com.qikserve.supermarket.service.impl;

import com.qikserve.supermarket.enuns.TypePromotion;
import com.qikserve.supermarket.model.Basket;
import com.qikserve.supermarket.model.Product;
import com.qikserve.supermarket.model.Promotion;
import com.qikserve.supermarket.service.BasketService;
import com.qikserve.supermarket.util.CalculationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {

    private final BuyXGetYFreeServiceImpl buyXGetYFreeServiceImpl;

    private final FlatPercentServiceImpl flatPercentServiceImpl;

    private final QtyBasedPriceOverrideServiceImpl qtyBasedPriceOverrideServiceImpl;

    private final List<Basket> baskets = new ArrayList<>();

    @Override
    public Basket create() {
        var basket = Basket.builder().id(1L).build();
        this.baskets.add(basket);
        return basket;
    }

    @Override
    public Basket addItem(Product product, Long id) {
        Optional<Basket> basket  = findBasketById(baskets, id);

        if (basket.isPresent()) {
            basket.get().addProduct(product);
            System.out.println("Produto adicionado ao Basket com ID 1.");
        } else {
            // Caso o Basket não seja encontrado
            System.out.println("Basket com ID 1 não encontrado.");
        }

        return basket.get();
    }

    public Double calculateTotalPromotion(Long id){
        return CalculationUtil.roundToTwoDecimalPlaces(calculateTotalCostNoApplyPromotion(id) - calculateTotalCostApplyingPromotion(id));
    }

    public Double calculateTotalCostNoApplyPromotion(Long id){
        Optional<Basket> basket = findBasketById(baskets, id);
        return CalculationUtil.roundToTwoDecimalPlaces(basket.get().getProducts().stream().mapToDouble(Product::getPrice).sum());
    }

    public Double calculateTotalCostApplyingPromotion(Long id){

        Optional<Basket> basket = findBasketById(baskets, id);
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