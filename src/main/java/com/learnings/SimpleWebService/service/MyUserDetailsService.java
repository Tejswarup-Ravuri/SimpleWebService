package com.learnings.SimpleWebService.service;

import com.learnings.SimpleWebService.model.UserPrinciple;
import com.learnings.SimpleWebService.model.Users;
import com.learnings.SimpleWebService.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //we need a user name and we get it from DB,
        // how do you get details from db it is using repo layer
        Users user=userepo.findByUsername(username);

        if(user==null){
            throw new UsernameNotFoundException("User Not Found");
        }

        //In general case you will return object of return type which is UserDetails,
        // but its an interface you cant create an object for interface and return it and  you want custom configurations
        // so you should create a class that implement the interface which is UserPrinciple in model package
        return new UserPrinciple(user);
    }
}
