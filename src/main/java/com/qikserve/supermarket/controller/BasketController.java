package com.qikserve.supermarket.controller;

import com.qikserve.supermarket.model.Basket;
import com.qikserve.supermarket.model.Product;
import com.qikserve.supermarket.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/baskets")
public class BasketController {


    private final BasketService basketService;

    @PostMapping("/create")
    public ResponseEntity<Basket> create() {
        final Basket basket = basketService.create();
        HttpHeaders headers = loadUri(basket);
        return new ResponseEntity<>(basket, headers, HttpStatus.CREATED);
    }

    @PatchMapping("/add/{id}")
    public ResponseEntity<Basket> addItem(@RequestBody Product product, @PathVariable("id") Long id) {
        final Basket updatedBasket = basketService.addItem(product, id);
        HttpHeaders headers = loadUri(updatedBasket);
        return new ResponseEntity<>(updatedBasket, headers, HttpStatus.OK);
    }

    @GetMapping("/totalCostApplyingPromotion/{id}")
    public ResponseEntity<Double> calculateTotalCostApplyingPromotion(@PathVariable("id") Long id) {
        final Double updatedBasket = basketService.calculateTotalCostApplyingPromotion(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "some-uri");
        return new ResponseEntity<>(updatedBasket, headers, HttpStatus.OK);
    }

    @GetMapping("/totalPromotion/{id}")
    public ResponseEntity<Double> calculateTotalPromotion(@PathVariable("id") Long id) {
        final Double updatedBasket = basketService.calculateTotalPromotion(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "some-uri");
        return new ResponseEntity<>(updatedBasket, headers, HttpStatus.OK);
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
