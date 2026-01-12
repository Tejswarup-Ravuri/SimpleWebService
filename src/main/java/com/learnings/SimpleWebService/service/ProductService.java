package com.learnings.SimpleWebService.service;

import com.learnings.SimpleWebService.model.Product;
import com.learnings.SimpleWebService.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo prod;

    public List<Product> getAllProducts(){
        return prod.findAll();
    }

    public Product getProductById(int id){
        return prod.findById(id).orElse(null);
    }


}
