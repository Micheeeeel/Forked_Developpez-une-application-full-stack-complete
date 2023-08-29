package com.openclassrooms.mddapi.model;

import java.io.Serializable;

public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String login;
    private String password;

    //need default constructor for JSON Parsing
    public JwtRequest()
    {

    }

//    public JwtRequest(String email, String password) {
//        this.setEmail(email);
//        this.setPassword(password);
//    }

    public String getLogin() {
        return this.login;
    }

//    public void setEmail(String email) {
//        this.email = email;
//    }

    public String getPassword() {
        return this.password;
    }

//    public void setPassword(String password) {
//        this.password = password;
//    }
}