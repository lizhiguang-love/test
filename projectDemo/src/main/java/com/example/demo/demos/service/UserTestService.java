package com.example.demo.demos.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.demos.pojo.UserTest;

import java.util.List;

public interface UserTestService extends IService<UserTest> {
    void batchInsert(List<UserTest> userTestList);
}
