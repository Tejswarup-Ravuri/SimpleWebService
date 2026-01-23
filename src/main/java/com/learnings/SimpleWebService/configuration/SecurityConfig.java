package com.learnings.SimpleWebService.configuration;

import io.micrometer.observation.annotation.ObservationKeyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

     @Bean
    public SecurityFilterChain implementSecurity(HttpSecurity http) {
        http.csrf(customizer -> customizer.disable());
        http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
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
        //
        //DaoAuthenticationProvider kicks in
        //
        //It extracts the username from the Authentication object.
        //
        //Then it calls your UserDetailsService.loadUserByUsername(username).
        //here we are customizing it so we pass userDetailsService object
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));//tells the provider how to check the password (e.g., BCrypt).

        return provider;
    }
}
