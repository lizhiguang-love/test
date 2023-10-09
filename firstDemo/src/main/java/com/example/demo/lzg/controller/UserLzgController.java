package com.example.demo.lzg.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.lzg.pojo.UserTest;
import com.example.demo.lzg.service.UserTestService;
import org.junit.runner.Result;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RefreshScope
public class UserLzgController {
    @Autowired
    private UserTestService userTestService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${person.age}")
    private String age;
    @GetMapping("/test")
    public String test(){
        return age;
    }

    @GetMapping("/query")
    public List<UserTest> queryUser(){
        IPage<UserTest> userTestIPage = userTestService.queryUser();
        List<UserTest> records = userTestIPage.getRecords();
        return records;
    }
    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage(){
        userTestService.sendDirectMessage();
        return "ok";
    }
    @GetMapping ("/getHello")
    public String getHelloWord(){

        return "hello world api get 接口数据";
    }

    @PostMapping("/postHello")
    public String postHelloWord(@RequestBody UserTest user){

        System.out.println(user.toString());
        return "hello world api post接口数据";
    }

}
