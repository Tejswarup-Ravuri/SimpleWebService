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

    @GetMapping("/products")
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

    @PutMapping("/products")
    public String updateProduct(@RequestBody Product product) {
        productservice.updateProduct(product);
        return "Product updated successfully";
    }

    @DeleteMapping("products/{prodId}")
    public String deleteProduct(@PathVariable int prodId){
        productservice.deleteProductById(prodId);
        return "Product deleted successfully";
    }


}
