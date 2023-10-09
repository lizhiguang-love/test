package com.example.demo.lzg.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.lzg.mapper.UserTestMapper;
import com.example.demo.lzg.other.BatchInsertUserTestThread;
import com.example.demo.lzg.pojo.UserTest;
import com.example.demo.lzg.service.UserTestService;
import com.google.common.collect.Lists;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeoutException;

@Service
public class UserTestServiceImpl extends ServiceImpl<UserTestMapper,UserTest> implements UserTestService  {
    @Resource
    private UserTestMapper userTestMapper;
    @Resource
    private Executor executor;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ConnectionFactory connectionFactory;

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

    @Override
    public void sendDirectMessage() {
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.confirmSelect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        String messageId=String.valueOf(UUID.randomUUID());
        String messageData="test message,hello!";
        String createTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        HashMap<String, Object> map = new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        rabbitTemplate.convertAndSend("testDirectExchange","testDirectRouting",map);

    }
}
