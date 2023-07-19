package com.example.demo.lzg.controller;

import com.example.demo.lzg.feign.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserClient userClient;

    @GetMapping("/secondDemo")
    public String test(){
        System.out.println("ccc");
        return userClient.tests();
    }
}
