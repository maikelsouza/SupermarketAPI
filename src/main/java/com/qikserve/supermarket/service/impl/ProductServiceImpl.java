package com.qikserve.supermarket.service.impl;

import com.qikserve.supermarket.client.ProductServiceClient;
import com.qikserve.supermarket.model.Product;
import com.qikserve.supermarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);


    private final ProductServiceClient productServiceClient;

    @Override
    public List<Map<String, Object>> fetchProducts() {
        logger.info("Find all products");
        return this.productServiceClient.fetchProducts();
    }

    @Override
    public Product fetchProductById(String id) {
        logger.info("Find products by id: {}", id);
        return this.productServiceClient.fetchProductById(id);
    }
}
