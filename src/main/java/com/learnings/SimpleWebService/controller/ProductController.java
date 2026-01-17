package com.learnings.SimpleWebService.controller;

import com.learnings.SimpleWebService.model.Product;
import com.learnings.SimpleWebService.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;

    @RequestMapping("/")
    public String greet(){
        return "Hi User!!";
    }

    @GetMapping ("/csrftoken")
    public CsrfToken getCsrfToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = service.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(product, HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/products")
//    public ResponseEntity<?> addProduct(@RequestPart Product prod, @RequestPart MultipartFile image){
//        try{
//            service.addProduct(prod,image);
//            return new ResponseEntity<>(HttpStatus.CREATED);
//        }
//        catch(Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody Product prod){
        try{
            service.addProduct(prod);
            return new ResponseEntity<>("Product Created Successfully",HttpStatus.CREATED);
        }
        catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PutMapping("/products/{id}")
//    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestPart Product prod, @RequestPart MultipartFile image) throws IOException {
//
//        Product product1=service.updateProduct(id,prod,image);
//
//        if(product1!=null){
//            return new ResponseEntity<>("Updated successfully",HttpStatus.OK);
//        }
//        else{
//            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
//        }
//
//    }

//    @DeleteMapping("/products/{id}")
//    public ResponseEntity<String> deleteProduct(@PathVariable int id){
//
//        Product product=service.getProductById(id);
//        if(product!=null){
//            service.deleteProduct(id);
//            return new ResponseEntity<>("Product deleted successfully",HttpStatus.OK);
//        }
//        else {
//            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
//        }
//    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id,@RequestBody Product product) throws IOException {

        Product product1=service.updateProduct(id,product);

        if(product1!=null){
            return new ResponseEntity<>("Updated successfully",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){

        Product product=service.getProductById(id);
        if(product!=null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successfully",HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> product = service.searchProducts(keyword);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(product, HttpStatus.NOT_FOUND);
        }


    }


}
