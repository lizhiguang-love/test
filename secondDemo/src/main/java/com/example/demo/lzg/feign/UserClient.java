package com.example.demo.lzg.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "firstDemo")
public interface UserClient {
    @GetMapping("/test")
    String tests();
}
