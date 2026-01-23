package com.learnings.SimpleWebService.repository;

import com.learnings.SimpleWebService.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Integer> {

    //Spring Data JPA looks at the method name in your repository interface.
    //It parses the name and automatically generates the query based on your entity’s fields.
    //SELECT * FROM users WHERE username = ?;
    Users findByUsername(String username); // must match entity field }


}
