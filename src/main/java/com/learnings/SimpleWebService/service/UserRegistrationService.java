package com.learnings.SimpleWebService.service;

import com.learnings.SimpleWebService.model.Users;
import com.learnings.SimpleWebService.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    @Autowired
    private UserRepo userepo;
    private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder(12);

    public void register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        userepo.save(user);
    }


}
