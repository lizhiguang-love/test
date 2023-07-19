package com.example.demo.lzg.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.lzg.pojo.UserTest;
import com.example.demo.lzg.service.UserTestService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
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
        String messageId=String.valueOf(UUID.randomUUID());
        String messageData="test message,hello!";
        String createTime=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        HashMap<String, Object> map = new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        rabbitTemplate.convertAndSend("testDirectExchange","testDirectRouting",map);
        return "ok";
    }
}
