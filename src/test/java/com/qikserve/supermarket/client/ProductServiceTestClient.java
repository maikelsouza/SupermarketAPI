package com.qikserve.supermarket.client;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceTestClient {

    private final RestTemplate restTemplate;

    public ProductServiceTestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Map<String, Object>> fetchProducts() {
        return restTemplate.getForObject( "http://localhost:8081/products", List.class);
    }

    public List<Map<String, Object>> fetchProductById(String id) {
        return restTemplate.getForObject("http://localhost:8081/products/"+id, List.class);
    }
}
