package com.learnings.SimpleWebService.controller;

import com.learnings.SimpleWebService.model.Product;
import com.learnings.SimpleWebService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class ProductController {
    @Autowired
    ProductService productservice;

    @RequestMapping("/products")
    public List<Product> getProductInfo()
    {
        return productservice.getProducts();
    }
}
