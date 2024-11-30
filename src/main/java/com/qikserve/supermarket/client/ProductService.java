package com.qikserve.supermarket.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Value("${api.url}")
    private String apiUrl;

    //private final RestTemplate restTemplate;

//    public ProductService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public List<Map<String, Object>> fetchProducts() {
//        return restTemplate.getForObject(apiUrl + "/products", List.class);
//    }
//
//    public List<Map<String, Object>> fetchProductById(String id) {
//        return restTemplate.getForObject(apiUrl + "/products/"+id, List.class);
//    }
}
