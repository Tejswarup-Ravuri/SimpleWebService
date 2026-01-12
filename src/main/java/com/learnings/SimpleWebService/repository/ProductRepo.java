package com.learnings.SimpleWebService.repository;

import com.learnings.SimpleWebService.model.Product;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>{

}

