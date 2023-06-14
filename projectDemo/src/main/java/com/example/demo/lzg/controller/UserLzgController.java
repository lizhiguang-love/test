package com.example.demo.lzg.controller;

import com.example.demo.lzg.service.UserTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class UserLzgController {
    @Autowired
    private UserTestService userTestService;
    @Value("${person.age}")
    private String age;
    @GetMapping("/test")
    public String test(){
        return age;
    }
}
