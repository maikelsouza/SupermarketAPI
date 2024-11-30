package com.qikserve.supermarket.service.impl;

import com.qikserve.supermarket.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Value("${api.url}")
    private String apiUrl;
}
