package com.qikserve.supermarket.service.impl;

import com.github.tomakehurst.wiremock.WireMockServer;

import com.qikserve.supermarket.client.ProductService;
import com.qikserve.supermarket.enuns.TypePromotion;
import com.qikserve.supermarket.model.Product;
import com.qikserve.supermarket.model.Promotion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
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

    @InjectMocks
    private WireMockServer wireMockServer;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        basketService.create();
        wireMockServer = new WireMockServer(8081);
        wireMockServer.start();
        configureFor("localhost", 8081);
        RestTemplate restTemplate = new RestTemplate();
        productService = new ProductService(restTemplate);
    }

    @AfterEach
    void teardown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @Test
    void calculateTotalCostApplyingPromotionNoPromotion() throws URISyntaxException, IOException {

        String mockResponse = new String(
                Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("twoBoringFriesProduct.json").toURI()))
        );
        stubFor(get(urlPathMatching("/products/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockResponse)));
        List<Map<String, Object>> twoBoringFriesProduct = this.productService.fetchProductById("4MB7UfpTQs");

        this.buildTwoProductionNoPromotion(twoBoringFriesProduct);


        Double totalCost = basketService.calculateTotalCostApplyingPromotion(1L);

        assertEquals(398.00, totalCost);
    }

    @Test
    void calculateTotalCostApplyingWithPromotionBuyXGetYFree() throws URISyntaxException, IOException {

        String mockResponse = new String(
                Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("twoBurgerProduct.json").toURI()))
        );
        stubFor(get(urlPathMatching("/products/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockResponse)));
        List<Map<String, Object>> twoBurgerProduct = this.productService.fetchProductById("PWWe3w1SDU");

        var product = this.buildTwoProductionWithPromotionBuyXGetYFree(twoBurgerProduct);

        when(buyXGetYFreeServiceImpl.applyDiscount(eq(product), any(), eq(2)))
                .thenReturn(999.00);

        Double totalCost = basketService.calculateTotalCostApplyingPromotion(1L);

        assertEquals(999.00, totalCost);
    }

    @Test
    void calculateTotalCostApplyingWithPromotionFlatPercent() throws URISyntaxException, IOException {

        String mockResponse = new String(
                Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("twoSaladProduct.json").toURI()))
        );
        stubFor(get(urlPathMatching("/products/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockResponse)));
        List<Map<String, Object>> twoSaladProduct = this.productService.fetchProductById("C8GDyLrHJb");

        var product = this.buildTwoProductionWithPromotionFlatPercent(twoSaladProduct);

        when(flatPercentServiceImpl.applyDiscount(eq(product), any(), eq(2)))
                .thenReturn(898.2);

        Double totalCost = basketService.calculateTotalCostApplyingPromotion(1L);

        assertEquals(898.2, totalCost);
    }

    @Test
    void calculateTotalCostApplyingWithPromotionQtyBasedPriceOverride() throws URISyntaxException, IOException {

        String mockResponse = new String(
                Files.readAllBytes(Paths.get(getClass().getClassLoader().getResource("twoPizzaProduct.json").toURI()))
        );
        stubFor(get(urlPathMatching("/products/.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(mockResponse)));

        List<Map<String, Object>> twoPizzaProduct = this.productService.fetchProductById("Dwt5F7KAhi");
        var product = this.buildTwoProductionWithPromotionQtyBasedPriceOverride(twoPizzaProduct);

        when(qtyBasedPriceOverrideServiceImpl.applyDiscount(eq(product), any(), eq(2)))
                .thenReturn(1799.00);
        Double totalCost = basketService.calculateTotalCostApplyingPromotion(1L);

        assertEquals(1799.00, totalCost);
    }

    private Product buildTwoProductionWithPromotionQtyBasedPriceOverride(List<Map<String, Object>> productMaps){
        Product product = null;
        for (Map<String, Object> productMap : productMaps) {
            product = mapToPizzaProduct(productMap);
            basketService.addItem(product,1L);
        }
        return product;
    }

    private Product buildTwoProductionWithPromotionFlatPercent(List<Map<String, Object>> productMaps){
        Product product = null;
        for (Map<String, Object> productMap : productMaps) {
            product = mapToSaladProduct(productMap);
            basketService.addItem(product,1L);
        }
        return product;
    }

    private Product buildTwoProductionWithPromotionBuyXGetYFree(List<Map<String, Object>> productMaps){
        Product product = null;
        for (Map<String, Object> productMap : productMaps) {
            product = mapToBurgerProduct(productMap);
            basketService.addItem(product,1L);
        }
        return product;
    }

    private static Product mapToPizzaProduct(Map<String, Object> productMap) {
        List<Promotion> promotions = new ArrayList<>();
        List<Map<String, Object>> promotionsList = (List<Map<String, Object>>) productMap.get("promotions");
        if (promotionsList != null) {
            for (Map<String, Object> promotionMap : promotionsList) {
                Promotion promotion = Promotion.builder()
                        .id((String) promotionMap.get("id"))
                        .typePromotion(TypePromotion.valueOf((String) promotionMap.get("type")))
                        .amount((Integer) promotionMap.get("required_qty"))
                        .price((Double) promotionMap.get("price"))
                        .build();
                promotions.add(promotion);
            }
        }

        return buildProduct(productMap, promotions);
    }

    private static Product buildProduct(Map<String, Object> productMap, List<Promotion> promotions) {
        return Product.builder()
                .id((String) productMap.get("id"))
                .name((String) productMap.get("name"))
                .price(((Number) productMap.get("price")).doubleValue())
                .promotions(promotions)
                .build();
    }

    private static Product mapToBurgerProduct(Map<String, Object> productMap) {
        List<Promotion> promotions = new ArrayList<>();
        List<Map<String, Object>> promotionsList = (List<Map<String, Object>>) productMap.get("promotions");
        if (promotionsList != null) {
            for (Map<String, Object> promotionMap : promotionsList) {
                Promotion promotion = Promotion.builder()
                        .id((String) promotionMap.get("id"))
                        .typePromotion(TypePromotion.valueOf((String) promotionMap.get("type")))
                        .requiredQty((Integer) promotionMap.get("required_qty"))
                        .freeQty((Integer) promotionMap.get("free_qty"))
                        .build();
                promotions.add(promotion);
            }
        }

        return buildProduct(productMap, promotions);
    }

    private static Product mapToSaladProduct(Map<String, Object> productMap) {
        List<Promotion> promotions = new ArrayList<>();
        List<Map<String, Object>> promotionsList = (List<Map<String, Object>>) productMap.get("promotions");
        if (promotionsList != null) {
            for (Map<String, Object> promotionMap : promotionsList) {
                Promotion promotion = Promotion.builder()
                        .id((String) promotionMap.get("id"))
                        .typePromotion(TypePromotion.valueOf((String) promotionMap.get("type")))
                        .amount((Integer) promotionMap.get("amount"))
                        .build();
                promotions.add(promotion);
            }
        }

        return buildProduct(productMap, promotions);
    }

    private void buildTwoProductionNoPromotion(List<Map<String, Object>> productMaps){
        for (Map<String, Object> productMap : productMaps) {
            Product product = buildProduct(productMap, (List<Promotion>) productMap.get("promotions"));
            basketService.addItem(product,1L);
        }
    }
}
