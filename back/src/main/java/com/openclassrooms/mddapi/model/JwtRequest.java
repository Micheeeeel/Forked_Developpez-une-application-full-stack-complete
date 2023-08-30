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

    public String getLogin() {
        return this.login;
    }


    public String getPassword() {
        return this.password;
    }

}