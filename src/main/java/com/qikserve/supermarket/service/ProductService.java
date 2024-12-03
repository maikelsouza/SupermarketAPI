package com.qikserve.supermarket.service;

import com.qikserve.supermarket.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {


    List<Map<String, Object>> fetchProducts();

    Product fetchProductById(String id);
}
