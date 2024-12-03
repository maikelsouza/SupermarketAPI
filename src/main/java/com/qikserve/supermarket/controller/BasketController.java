package com.qikserve.supermarket.controller;

import com.qikserve.supermarket.model.Basket;
import com.qikserve.supermarket.model.Product;
import com.qikserve.supermarket.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/baskets")
public class BasketController {


    private static final Logger logger = LogManager.getLogger(BasketController.class);

    private final BasketService basketService;

    @PostMapping("/create")
    public ResponseEntity<Basket> create() {
        logger.info("Request to create a new Basket received.");
        try {
            final Basket basket = basketService.create();
            HttpHeaders headers = loadUri(basket);
            logger.info("Basket created successfully with ID: {}", basket.getId());
            return new ResponseEntity<>(basket, headers, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error occurred while creating the Basket: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/add/{id}")
    public ResponseEntity<Basket> addItem(@RequestBody Product product, @PathVariable("id") Long id) {
        logger.info("Request to add an item to the Basket.");
        try {
            final Basket updatedBasket = basketService.addItem(product, id);
            HttpHeaders headers = loadUri(updatedBasket);
            return new ResponseEntity<>(updatedBasket, headers, HttpStatus.OK);
        } catch (Exception e) {
        logger.error("Error occurred while adding an item to the Basket: {}", e.getMessage(), e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }

    @GetMapping("/totalCostApplyingPromotion/{id}")
    public ResponseEntity<Double> calculateTotalCostApplyingPromotion(@PathVariable("id") Long id) {
        logger.info("Request to calculate Total Cost applying Promotion.");
        try {
            final Double updatedBasket = basketService.calculateTotalCostApplyingPromotion(id);
            HttpHeaders headers = getHttpHeaders();
            return new ResponseEntity<>(updatedBasket, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error("Error occurred while to calculate Total Cost applying Promotion: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/totalPromotion/{id}")
    public ResponseEntity<Double> calculateTotalPromotion(@PathVariable("id") Long id) {
        logger.info("Request to calculate Total Promotion.");
        try{
            final Double updatedBasket = basketService.calculateTotalPromotion(id);
            HttpHeaders headers = getHttpHeaders();
            return new ResponseEntity<>(updatedBasket, headers, HttpStatus.OK);
        } catch (Exception e){
            logger.error("Error occurred while to calculate Total Promotion: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    static HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "some-uri");
        return headers;
    }

    private HttpHeaders loadUri(Basket basket) {
        final String uri = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/baskets/{id}")
                .buildAndExpand(basket.getId())
                .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);
        return headers;
    }
}
