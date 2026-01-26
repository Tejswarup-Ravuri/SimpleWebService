package com.learnings.SimpleWebService.configuration;


import com.learnings.SimpleWebService.service.JwtService;
import com.learnings.SimpleWebService.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
//Its job: look at the request, check if there’s a JWT, and if valid, set authentication object.
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
//    It decides whether the request has a valid JWT and sets authentication accordingly.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//  Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraWxsIiwiaWF0IjoxNzIzMTgzNzExLCJleHAiOjE3MjMxODM4MTl9.5nf7dRzKRiuGurN2B9dHh_M5xiu73ZzWPr6rbhOTTHs
        String authHeader = request.getHeader("Authorization");//get headers
        String token = null;
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); //remove "Bearer " prefix
            username = jwtService.extractUserName(token); // read username from token
        }

        //If we found a username and the user is not already authenticated.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Load user details from your database (MyUserDetailsService).
            // For that we are using Application context the main container
            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
            //Ensures the token is not expired and matches the user.
            if (jwtService.validateToken(token, userDetails)) {
                //If valid, create a UsernamePasswordAuthenticationToken.
                //This tells Spring Security which user is logged in.
                //Without this, Spring wouldn’t know who the request belongs to.
                //This is Spring Security’s way of saying: “This user is authenticated.”
                //UsernamePasswordAuthenticationToken is a Spring Security object that represents a logged‑in user.
                //Think of this as creating a badge for the user that says:
                //“Here’s John, he’s authenticated, and he has these roles.”
                // Now Spring Security knows the user is logged in and what roles they have.

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());


                //This adds extra info about the request:like ip address,browser info
                //It’s like stamping the badge with:
                //“John logged in from Chrome at 192.168.1.10.”
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                //Put this authentication object into SecurityContextHolder.
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        //Passes the request along to the next filter or controller.
        //If authentication was set, the controller now sees the user as logged in.
        //If not, the request continues unauthenticated.

        filterChain.doFilter(request, response);
    }
}