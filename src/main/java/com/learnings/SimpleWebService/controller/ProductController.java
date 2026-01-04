package com.learnings.SimpleWebService.controller;

import com.learnings.SimpleWebService.model.Product;
import com.learnings.SimpleWebService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/products/{prodId}")
    public Product getProductById(@PathVariable int prodId){
        return productservice.getProductByProdId(prodId);
    }

    @PostMapping("/products")
    public String addProduct( @RequestBody Product product){
        productservice.addProduct(product);
        return "Product added successfully";
    }

}
