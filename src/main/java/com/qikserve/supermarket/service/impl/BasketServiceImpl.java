package com.qikserve.supermarket.service.impl;

import com.qikserve.supermarket.enuns.TypePromotion;
import com.qikserve.supermarket.model.Basket;
import com.qikserve.supermarket.model.Product;
import com.qikserve.supermarket.service.BasketService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BasketServiceImpl implements BasketService {

    private BuyXGetYFreeServiceImpl buyXGetYFreeServiceImpl;

    private FlatPercentServiceImpl flatPercentServiceImpl;

    private QtyBasedPriceOverrideServiceImpl qtyBasedPriceOverrideServiceImpl;


    List<Basket> baskets = new ArrayList<>();

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


    public Double calculateTotalPromotion(Basket basket){
        return calculateTotalCostNoApplyPromotion(basket) - calculateTotalCostApplyingPromotion(basket);
    }

    public Double calculateTotalCostNoApplyPromotion(Basket basket){
        return basket.getProducts().stream().mapToDouble(Product::getPrice).sum();
    }

    public Double calculateTotalCostApplyingPromotion(Basket basket){

        var products = basket.getProducts();
        Double totalCost = 0.0;

        List<String> productIds = products
                .stream()
                .map(Product::getId)
                .toList();

        Map<String, List<Product>> groupedById = products.stream()
                .collect(Collectors.groupingBy(Product::getId));


        for (String productId: productIds) {
            List<Product> productsGropuById = groupedById.get(productId);
            int quantity = productsGropuById.size();

            for (Product product: productsGropuById){
                if (product.getPromotion() == null){
                    totalCost += product.getPrice();
                }else{
                    if (product.getPromotion().getTypePromotion() == TypePromotion.FLAT_PERCENT){
                        totalCost += flatPercentServiceImpl.applyDiscount(product,product.getPromotion(), quantity);
                    }
                    if (product.getPromotion().getTypePromotion() == TypePromotion.BUY_X_GET_Y_FREE){
                        totalCost += buyXGetYFreeServiceImpl.applyDiscount(product,product.getPromotion(), quantity);
                    }
                    if (product.getPromotion().getTypePromotion() == TypePromotion.QTY_BASED_PRICE_OVERRIDE){
                        totalCost += qtyBasedPriceOverrideServiceImpl.applyDiscount(product,product.getPromotion(), quantity);
                    }
                }
            }
        }
        return totalCost;
    }


    public static Optional<Basket> findBasketById(List<Basket> baskets, Long id) {
        return baskets.stream()
                .filter(basket -> basket.getId() == id)
                .findFirst();
    }

}