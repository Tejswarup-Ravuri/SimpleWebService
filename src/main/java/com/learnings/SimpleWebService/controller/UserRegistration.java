package com.learnings.SimpleWebService.controller;

import com.learnings.SimpleWebService.model.Users;
import com.learnings.SimpleWebService.service.UserCredDetails;
import com.learnings.SimpleWebService.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reg")
public class UserRegistration {
    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private UserCredDetails userCredDetails;

    @GetMapping("/")
    public String greet(){
        return "Hello user! please enter your creds";
    }

    @PostMapping("/register")
    public String createUser(@RequestBody Users user){
        userRegistrationService.register(user);
        return "User Registered successfully";
    }

    @GetMapping("/Users")
    public List<Users> GetUserList(){

        return userCredDetails.getUserList();

    }

    @PostMapping("/login")
    public String userLogin(@RequestBody Users user){
        return userRegistrationService.verifyUser(user);

    }




}
