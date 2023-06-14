package com.example.demo.lzg.other;

import com.example.demo.lzg.mapper.UserTestMapper;
import com.example.demo.lzg.pojo.UserTest;

import java.util.List;
import java.util.concurrent.Callable;

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
