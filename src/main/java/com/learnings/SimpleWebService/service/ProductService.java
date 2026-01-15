package com.learnings.SimpleWebService.service;

import com.learnings.SimpleWebService.model.Product;
import com.learnings.SimpleWebService.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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


    public Product addProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageData(image.getBytes());
        product.setImageType(image.getContentType());
        return prod.save(product);
    }
}
