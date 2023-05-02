package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.demos.mapper.UserTestMapper;
import com.example.demo.demos.pojo.UserTest;
import com.example.demo.demos.service.UserTestService;
import com.example.demo.demos.util.RedissonLock;
import com.example.thread.InsertTarget;
import com.example.thread.InsertTargetCallable;
import com.example.thread.InsertTargetRunnable;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest

class DemoApplicationTests {
    @Resource
    private UserTestMapper userTestMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserTestService userTestService;

    @Resource
    private Executor executor;

    @Resource
    private RedissonLock redissonLock;
    @Value("${spring.task.execution.pool.core-size}")
    private String corePoolSize;

    @Test
    void contextLoads() {
        List<UserTest> userTests = userTestMapper.selectList(null);
        userTests.forEach(userTest -> {
            userTest.getAge();
            System.out.println(userTest.getAge());
        });

        System.out.println(corePoolSize);
    }

    @Test
    void redisTest(){
        System.out.println(stringRedisTemplate.opsForValue().get("test"));
    }
    @Test
    void testInsert(){
        ArrayList<UserTest> allUser = new ArrayList(5000);
        //生成size个UserInfo
        for (int i = 0; i < 20; i++) {
            UserTest userInfo = new UserTest();
            userInfo.setName("test"+i);
            userInfo.setEmail("email"+i);
            userInfo.setAge(i);
            allUser.add(userInfo);
        }
        userTestService.batchInsert(allUser);
//        long  startTime = System.currentTimeMillis();
//        for (int i = 0;i<5000;i++){
//            userTestMapper.insert(allUser.get(i));
//        }
//        System.out.println("单线程for循环插入5000耗时:"+(System.currentTimeMillis()-startTime));

    }
    @Test
    void BatchInsert(){
        ArrayList<UserTest> allUser = new ArrayList(5000);
        //生成size个UserInfo
        for (int i = 0; i < 5000; i++) {
            UserTest userInfo = new UserTest();
            userInfo.setName("test"+i);
            userInfo.setEmail("email"+i);
            userInfo.setAge(i);
            allUser.add(userInfo);
        }

        //每个线程处理的数据量
//        final Integer dataPartionLength=(allUser.size()+50-1)/50;

        int downLanchSize=0;
        //将downLanchSize的值算出来
        if (allUser.size()%100==0){
            downLanchSize=allUser.size()/100;
        }else {
            downLanchSize=allUser.size()/100+1;
        }
        CountDownLatch countDownLatch = new CountDownLatch(downLanchSize);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < downLanchSize; i++) {
            //每个线程均分需要处理的数据
            List<List<UserTest>> partition = Lists.partition(allUser, 100);
            List<UserTest> userTests = partition.get(i);
            //调用实现了Callable的InsertTarget类，将countDownLatch,partion传进去
//            InsertTarget insertTarget = new InsertTarget(partition.get(i),countDownLatch,userTestMapper);
            CompletableFuture.runAsync(()->{
                try {
                    new InsertTarget(userTests,countDownLatch,userTestMapper).call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },executor);
//            FutureTask futureTask = new FutureTask(insertTarget);
//            //执行任务
//            executor.execute(futureTask);
        }


//        try {
//            //保证每个线程完成后再完成计时
//            countDownLatch.await();
//            long endTime = System.currentTimeMillis();
//            System.out.println("线程数为{}的线程池插入5000用时--->"+(endTime - startTime));
//
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }

    }
    @Test
    void CompleteFutureTest(){
        CompletableFuture<String> uCompletableFuture = CompletableFuture.supplyAsync(()->{
            new InsertTargetRunnable().run();
            System.out.println("呵呵");
            return "world";
        }, executor);
        try {
            System.out.println(uCompletableFuture.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    @Test
    void completeableFutureTest(){
//        CompletableFuture.runAsync(new InsertTargetRunnable(), executor);
        CompletableFuture<List<UserTest>> userTestList1 = CompletableFuture.supplyAsync(() -> {
            QueryWrapper<UserTest> queryWrapper = new QueryWrapper<>();
            queryWrapper.le("age", 10);
            List<UserTest> userTests = userTestMapper.selectList(queryWrapper);
            return userTests;
        }, executor);
        CompletableFuture<List<UserTest>> userTestList2 = CompletableFuture.supplyAsync(() -> {
            QueryWrapper<UserTest> queryWrapper = new QueryWrapper<>();
            queryWrapper.gt("age", 10);
            List<UserTest> userTests = userTestMapper.selectList(queryWrapper);
            return userTests;
        }, executor);
        try {
            System.out.println(userTestList1.get().get(0));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    @Test
    void selectTest(){
//        List<UserTest> userTests = userTestMapper.selectUserTest();
//        userTests.forEach(userTest->{
//            System.out.println(userTest.getEmail());
//        });
        UserTest userTest = new UserTest();
        userTest.setAge(18);
        userTest.setName("test");
        userTest.setEmail("110@qq.com");
        int insert = userTestMapper.insert(userTest);
        System.out.println(insert);
    }
    @Test
    void ListTest(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        List<List<Integer>> partition = Lists.partition(list, 5/2);
        System.out.println(partition.size());
    }
    @Test
    void jedisTest(){
        //模拟10个客户端
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new LockRunnable());
            thread.start();
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private class LockRunnable implements Runnable{

        @Override
        public void run() {
            redissonLock.addLock("demo");
            try {
                TimeUnit.SECONDS.sleep(11);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            redissonLock.releaseLock("demo");
        }
    }
}
