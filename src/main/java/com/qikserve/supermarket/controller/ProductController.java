package com.qikserve.supermarket.controller;

import com.qikserve.supermarket.model.Product;
import com.qikserve.supermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.qikserve.supermarket.controller.BasketController.getHttpHeaders;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/products")
public class ProductController {

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    private final ProductService productServiceImpl;

    @GetMapping("/")
    public ResponseEntity<List<Map<String, Object>>> findAllProducts() {
        logger.info("Request to find all Products.");
        try {
            final List<Map<String, Object>> products = productServiceImpl.fetchProducts();
            HttpHeaders headers = getHttpHeaders();
            return new ResponseEntity<>(products, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error("Error occurred while to find all products: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findProductsById(@PathVariable("id") String id) {
        logger.info("Request to find product by id.");
        try {
            final Product  product = productServiceImpl.fetchProductById(id);
            HttpHeaders headers = getHttpHeaders();
            return new ResponseEntity<>(product, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            logger.error("Error occurred while to find product by id: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
