package com.example.demo.demos.other;

import com.example.demo.demos.mapper.UserTestMapper;
import com.example.demo.demos.pojo.UserTest;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class BatchInsertUserTestThread implements Callable {
    private UserTestMapper userTestMapper;
    private List<UserTest> userTestList;
    public BatchInsertUserTestThread(UserTestMapper userTestMapper,List<UserTest> userTestList){
        this.userTestMapper=userTestMapper;
        this.userTestList=userTestList;
    }
    @Override
    public Object call() throws Exception {
        userTestList.forEach(userTest -> {
            userTestMapper.insert(userTest);
        });

        return "success";
    }
}
