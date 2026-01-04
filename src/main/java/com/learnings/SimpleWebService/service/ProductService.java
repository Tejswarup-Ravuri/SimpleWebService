package com.learnings.SimpleWebService.service;

import com.learnings.SimpleWebService.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    List<Product> products= new ArrayList<>(Arrays.asList(new Product(101,"IQOO",30000),
            new Product(201,"Iphone",50000)));
    public List<Product> getProducts(){

        return products;
    }

    public Product getProductByProdId(int prodId){
        return products.stream().filter(p->p.getProductId()==prodId).findFirst().get();
    }

    public void addProduct(Product productFromInput){
        products.add(productFromInput);
    }

}
