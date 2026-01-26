package com.learnings.SimpleWebService.configuration;

import io.micrometer.observation.annotation.ObservationKeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//User enters credentials
//→ Username + password are sent in the login request.
//
//Spring creates an unauthenticated token
//→ UsernamePasswordAuthenticationToken(username, password) is created.
//        → At this point, it’s just a “ticket request” — not yet validated.
//
//AuthenticationManager takes the token
//→ Think of it as the “head bouncer” at the club.
//→ He doesn’t check IDs himself, he passes the token to the right specialist.
//
//AuthenticationProvider (DaoAuthenticationProvider) is called
//→ This is the “specialist bouncer” who knows how to check usernames and passwords.
//
//DaoAuthenticationProvider asks the database via UserDetailsService
//→ “Do we have a user with this username?”
//        → UserDetailsService.loadUserByUsername(username) fetches the stored user record (including the hashed password).
//
//Password check happens
//→ Dao takes the raw password the user typed.
//        → Uses BCryptPasswordEncoder.matches(rawPassword, storedHash) to compare.
//        → Important: It does not decrypt the DB password. It re‑hashes the raw password and compares fingerprints.
//
//If match → authenticated object created
//→ Dao builds a new Authentication object with stamp as auntheticated.
//→ This object contains the user’s details and roles.
//
//AuthenticationManager returns the result
//→ Back to your code, you now hold the “stamped ticket” (the authenticated object).and check t authenticated or not

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private UserDetailsService userDetailsService;



     @Bean
    public SecurityFilterChain implementSecurity(HttpSecurity http) {
        http.csrf(customizer -> customizer.disable());
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/reg/register","/reg/login")
                .permitAll()
                .anyRequest().authenticated());
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);

        return http.build();
}
    @Bean
    public AuthenticationProvider authenticationProvider() {
        //User submits login form (or sends credentials via HTTP Basic).
        //
        //The request contains the username and password.
        //
        //Spring Security creates an Authentication object
        //
        //Specifically, a UsernamePasswordAuthenticationToken with the username and password from the form.
        //UsernamePasswordAuthenticationToken is an authentication object for the current request,
        // but the contents of that object represent the user’s identity and roles.
        //DaoAuthenticationProvider kicks in
        //
        //It extracts the username from the Authentication object.
        //
        //Then it calls your UserDetailsService.loadUserByUsername(username).
        //here we are customizing it so we pass userDetailsService object
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

        //internally it will hash the password and check wheather its matching with the one in db or not
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));//tells the provider how to check the password (e.g., BCrypt).

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config){
        return config.getAuthenticationManager();
    }
}
