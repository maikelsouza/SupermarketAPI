package com.qikserve.supermarket.service.impl;

import com.qikserve.supermarket.enuns.TypePromotion;
import com.qikserve.supermarket.model.Product;
import com.qikserve.supermarket.model.Promotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BasketServiceImplTest {

    @InjectMocks
    private BasketServiceImpl basketService;

    @Mock
    private BuyXGetYFreeServiceImpl buyXGetYFreeServiceImpl;

    @Mock
    private FlatPercentServiceImpl flatPercentServiceImpl;

    @Mock
    private QtyBasedPriceOverrideServiceImpl qtyBasedPriceOverrideServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        basketService.create();
    }

    @Test
    void calculateTotalCostApplyingPromotionNoPromotion() {
        this.buildTwoProductionNoPromotion();
        Double totalCost = basketService.calculateTotalCostApplyingPromotion(1L);

        assertEquals(398.00, totalCost);
    }

    @Test
    void calculateTotalCostApplyingWithPromotionBuyXGetYFree() {

        this.buildTwoProductionWithPromotionBuyXGetYFree();
        when(buyXGetYFreeServiceImpl.applyDiscount(eq(buildBurgerProduct()), any(), eq(2)))
                .thenReturn(999.00);
        Double totalCost = basketService.calculateTotalCostApplyingPromotion(1L);

        assertEquals(999.00, totalCost);
    }

    @Test
    void calculateTotalCostApplyingWithPromotionFlatPercent() {

        this.buildTwoProductionWithPromotionFlatPercent();
        when(flatPercentServiceImpl.applyDiscount(eq(buildSaladProduct()), any(), eq(2)))
                .thenReturn(898.2);
        Double totalCost = basketService.calculateTotalCostApplyingPromotion(1L);

        assertEquals(898.2, totalCost);
    }

    @Test
    void calculateTotalCostApplyingWithPromotionQtyBasedPriceOverride() {

        this.buildTwoProductionWithPromotionQtyBasedPriceOverride();
        when(qtyBasedPriceOverrideServiceImpl.applyDiscount(eq(buildPizzaProduct()), any(), eq(2)))
                .thenReturn(1799.00);
        Double totalCost = basketService.calculateTotalCostApplyingPromotion(1L);

        assertEquals(1799.00, totalCost);
    }

    private void buildTwoProductionWithPromotionQtyBasedPriceOverride(){
        basketService.addItem(buildPizzaProduct(), 1L);
        basketService.addItem(buildPizzaProduct(), 1L);
    }

    private void buildTwoProductionWithPromotionFlatPercent(){
        basketService.addItem(buildSaladProduct(), 1L);
        basketService.addItem(buildSaladProduct(), 1L);
    }

    private void buildTwoProductionWithPromotionBuyXGetYFree(){
        basketService.addItem(buildBurgerProduct(), 1L);
        basketService.addItem(buildBurgerProduct(), 1L);
    }

    private void buildTwoProductionNoPromotion(){
        basketService.addItem(buildBoringFriesProduct(), 1L);
        basketService.addItem(buildBoringFriesProduct(), 1L);
    }

    private Product buildBoringFriesProduct() {
        return Product.builder()
                .id("4MB7UfpTQs")
                .name("Boring Fries!")
                .price(199.00)
                .promotions(new ArrayList<>())
                .build();
    }

    private Product buildPizzaProduct() {
        return Product.builder()
                .id("Dwt5F7KAhi")
                .name("Amazing Pizza!")
                .price(1099.00)
                .promotions(Arrays.asList(buildPromotionQtyBasedPriceOverride()))
                .build();
    }


    private Product buildSaladProduct() {
        return Product.builder()
                .id("C8GDyLrHJb")
                .name("Amazing Salad!")
                .price(499.00)
                .promotions(Arrays.asList(buildPromotionFlatPercent()))
                .build();
    }

    private Product buildBurgerProduct() {
        return Product.builder()
                .id("PWWe3w1SDU")
                .name("Amazing Burger!")
                .price(999.00)
                .promotions(Arrays.asList(buildPromotionBuyXGetYFree()))
                .build();
    }

    private Promotion buildPromotionBuyXGetYFree(){
        return Promotion.builder()
                .id("ZRAwbsO2qM")
                .requiredQty(2)
                .freeQty(1)
                .typePromotion(TypePromotion.BUY_X_GET_Y_FREE)
                .build();
    }

    private Promotion buildPromotionFlatPercent(){
        return Promotion.builder()
                .id("Gm1piPn7Fg")
                .amount(10)
                .typePromotion(TypePromotion.FLAT_PERCENT)
                .build();
    }

    private Promotion buildPromotionQtyBasedPriceOverride(){
        return Promotion.builder()
                .id("ibt3EEYczW")
                .requiredQty(2)
                .price(1799.00)
                .typePromotion(TypePromotion.QTY_BASED_PRICE_OVERRIDE)
                .build();
    }

}
