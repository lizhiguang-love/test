package com.example.thread;

import com.example.demo.demos.mapper.UserTestMapper;
import com.example.demo.demos.pojo.UserTest;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
public class InsertTarget implements Callable {

    private UserTestMapper userTestMapper;

    private List<UserTest> userTestList;
    private CountDownLatch countDownLatch;

    int rows=0;
    public InsertTarget(List<UserTest> userTestList,CountDownLatch countDownLatch,UserTestMapper userTestMapper){
        this.countDownLatch=countDownLatch;
        this.userTestList=userTestList;
        this.userTestMapper=userTestMapper;
    }
    public Integer call() throws Exception {
        for (UserTest userTest : userTestList) {
            userTestMapper.insert(userTest);
            rows++;
        }

        if (rows==userTestList.size()){
            countDownLatch.countDown();
        }
        return rows;
    }
}
