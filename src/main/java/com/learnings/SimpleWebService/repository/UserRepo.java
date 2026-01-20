package com.learnings.SimpleWebService.repository;

import com.learnings.SimpleWebService.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Integer> {
    Users findByUsername(String username); // must match entity field }
}
