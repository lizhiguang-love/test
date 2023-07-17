package com.example.thread;

import com.example.demo.lzg.mapper.UserTestMapper;
import com.example.demo.lzg.pojo.UserTest;

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
            System.out.println("userTest");
            userTestMapper.insert(userTest);

            rows++;
        }
        System.out.println("累计--->"+rows);
//        if (rows==userTestList.size()){
//            countDownLatch.countDown();
//        }
        return rows;
    }
}
