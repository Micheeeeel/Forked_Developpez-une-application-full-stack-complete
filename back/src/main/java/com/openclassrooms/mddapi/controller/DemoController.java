package com.openclassrooms.mddapi.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/")
public class DemoController {
    @GetMapping("/protected")
    public String helloSecure(){
        return "Hello Autour Du Code protected !";
    }

    @GetMapping("/public")
    public String helloPublic(){
        return "Hello Autour Du Code public !";
    }
}
