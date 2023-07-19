package com.example.demo.lzg.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.lzg.mapper.UserTestMapper;
import com.example.demo.lzg.other.BatchInsertUserTestThread;
import com.example.demo.lzg.pojo.UserTest;
import com.example.demo.lzg.service.UserTestService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;

@Service
public class UserTestServiceImpl extends ServiceImpl<UserTestMapper,UserTest> implements UserTestService  {
    @Resource
    private UserTestMapper userTestMapper;
    @Resource
    private Executor executor;

    @Value("${spring.task.execution.pool.core-size}")
    private String corePoolSize;

    @Override
    public void batchInsert(List<UserTest> userTestList) {
        //每个线程处理的数据量
        int downLanchSize=userTestList.size()%Integer.valueOf(corePoolSize)==0 ? Integer.valueOf(corePoolSize) : Integer.valueOf(corePoolSize)+1;
        BatchInsertUserTestThread userTestThread=null;
        //当数据量小于核心线程数时
        if (userTestList.size()<Integer.valueOf(corePoolSize)){
            for (UserTest userTest : userTestList) {
                CompletableFuture.runAsync(()->{
                    baseMapper.insert(userTest);
                });
            }

            userTestThread = new BatchInsertUserTestThread(userTestMapper,userTestList);
            FutureTask futureTask = new FutureTask(userTestThread);
            executor.execute(futureTask);
        }else {
            for (int i = 0; i < downLanchSize; i++) {
                List<List<UserTest>> partition = Lists.partition(userTestList, userTestList.size() / Integer.valueOf(corePoolSize));
                //为每一个线程分配任务
                userTestThread = new BatchInsertUserTestThread(userTestMapper, partition.get(i));
                FutureTask futureTask = new FutureTask(userTestThread);
                executor.execute(futureTask);
            }
        }


    }

    @Override
    public IPage<UserTest> queryUser() {
        Page<UserTest> page = new Page<>(1, 2);
        Page<UserTest> userTestPage = baseMapper.selectPage(page, null);
        return userTestPage;
    }
}
