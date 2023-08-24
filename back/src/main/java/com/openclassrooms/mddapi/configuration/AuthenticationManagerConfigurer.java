package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationManagerConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Ici, vous injectez votre UserDetailsService et votre PasswordEncoder
        // dans AuthenticationManagerBuilder.
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(jwtUserDetailsService).passwordEncoder(bcryptEncoder);
    }
}
