package com.learnings.SimpleWebService.service;

import com.learnings.SimpleWebService.model.Users;
import com.learnings.SimpleWebService.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCredDetails {

    @Autowired
    private UserRepo userrepo;

    public List<Users> getUserList(){
        return userrepo.findAll();
    }
}
