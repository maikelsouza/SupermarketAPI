package com.qikserve.supermarket.client;

import com.qikserve.supermarket.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceClient {

    private final RestTemplate restTemplate;

    public ProductServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> fetchProducts() {
        return restTemplate.getForObject( "http://localhost:8081/products", List.class);
    }

    public Product fetchProductById(String id) {
        return restTemplate.getForObject("http://localhost:8081/products/"+id, Product.class);
    }
}
