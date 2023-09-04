package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // In case there are roles or authorities assigned to the user, return them here.
        // In the simplest case, we can return an empty list
        return Collections.emptyList();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Add logic here, for simplicity return true
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Add logic here, for simplicity return true
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Add logic here, for simplicity return true
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Add logic here, for simplicity return true
        return true;
    }



}
