package com.learnings.SimpleWebService.service;

import com.learnings.SimpleWebService.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    List<Product> products= new ArrayList<>(Arrays.asList(new Product(101,"IQOO",30000),
            new Product(102,"Iphone",50000)));
    public List<Product> getProducts(){

        return products;
    }

    public Product getProductByProdId(int prodId){
        return products.stream().filter(p->p.getProductId()==prodId).findFirst().get();
    }

    public void addProduct(Product productFromInput){
        products.add(productFromInput);
    }

    public void updateProduct(Product prod){
        int idx=0;
        for(int i=0;i<products.size();i++){
            if(products.get(i).getProductId()==prod.getProductId()){
                idx=i;
            }
        }

        products.set(idx,prod);
    }

    public void deleteProductById(int prodId){
        int idx=0;
        for(int i=0;i<products.size();i++) {
            if (products.get(i).getProductId() == prodId) {
                idx = i;
            }
        }
        products.remove(idx);
    }

}
