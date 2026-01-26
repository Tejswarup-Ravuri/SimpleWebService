package com.learnings.SimpleWebService.service;

import com.learnings.SimpleWebService.model.Users;
import com.learnings.SimpleWebService.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {
    @Autowired
    private UserRepo userepo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void register(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userepo.save(user);
    }


    public String verifyUser(Users user) {
        //UsernamePasswordAuthenticationToken
        // this is where AuthenticationManager internally calls AuthenticationpProvider which is Dao
        //Dao checks the password and if matches it will stamp is as authenticated.
        // and that is what will be holded by authentication obj here
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        //we check that stamp
        if (authentication.isAuthenticated()) {
//            return "Success";
            return jwtService.generateToken(user.getUsername());
        }
        return "Failure";
    }
}
