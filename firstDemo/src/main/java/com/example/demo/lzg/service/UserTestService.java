package com.example.demo.lzg.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.lzg.pojo.UserTest;

import java.util.List;

public interface UserTestService extends IService<UserTest> {
    void batchInsert(List<UserTest> userTestList);

    IPage<UserTest> queryUser();

    void sendDirectMessage();
}
