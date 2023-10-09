package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.lzg.mapper.UserTestMapper;
import com.example.demo.lzg.pojo.UserTest;
import com.example.demo.lzg.service.UserTestService;
import com.example.demo.lzg.util.RedissonLock;
import com.example.thread.InsertTarget;
import com.example.thread.InsertTargetRunnable;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.junit.runner.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
    private Jedis jedis;

    @Resource
    private RedisTemplate redisTemplate;

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
//        模拟多个5个客户端
//        for (int i=0;i<5;i++) {
//            Thread thread = new Thread(new LockRunnable());
//            thread.start();
//        }
        jedis.set("qqq","aaa");
        System.out.println(jedis.get("qqq"));
        stringRedisTemplate.opsForValue().set("hello","world");
        jedis.rpush("key1","value1","value2");
        System.out.println(stringRedisTemplate.opsForValue().get("hello"));
//        String uuid = UUID.randomUUID().toString();
//        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent("uuid", uuid,10,TimeUnit.SECONDS);
//        RedisLockDemo redisLockDemo = new RedisLockDemo(stringRedisTemplate,jedis);
//        redisLockDemo.test();
        //        System.out.println(result);
//        stringRedisTemplate.opsForValue().set("hello","world",5,TimeUnit.SECONDS);

//        System.out.println(stringRedisTemplate.opsForValue().get("hello"));
    }

    @Test
    void testInsert(){
        UserTest userTest = new UserTest();
        userTest.setAge(50);
        userTest.setEmail("110");
        userTest.setName("小帅");
        userTestService.save(userTest);
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
        HashMap<String, String> map = new HashMap<>();
        concurrentHashMap.put("test","123");
        Set<Map.Entry<String, String>> entrySet = concurrentHashMap.entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next().getValue());
        }
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.add(5);
//
//        List<List<Integer>> partition = Lists.partition(list, 5/2);
//        System.out.println(partition.size());
    }
    @Test
    void jedisTest(){
        //模拟10个客户端
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);

    }
    private class LockRunnable implements Runnable{

        @Override
        public void run() {

                System.out.println("进来了");
                String uuid = UUID.randomUUID().toString();
                jedis.set("tes","test");
                System.out.println(jedis.get("tes"));


        }
    }
    @Test
    void arrayTest(){
        int[] num={7,4,1,3,5};
        int temp;
        for (int i = 0; i < num.length; i++) {
            for (int j=0;j<num.length-1;j++){
                if (num[j]>num[j+1]){
                    temp=num[j];
                    num[j]=num[j+1];
                    num[j+1]=temp;
                }
            }
        }
        for (int i = 0; i < num.length; i++) {
            System.out.println(num[i]);
        }
    }
    @Test
    void ioTest(){
        String str="D:\\ioTest.txt";
        FileInputStream fileInputStream=null;
        InputStreamReader reader=null;
        try {
             fileInputStream = new FileInputStream(str);
             reader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            char[] chars=new char[1];
            int readLine;
            while ((readLine = bufferedInputStream.read()) != -1){
                System.out.println(readLine);
            }
//            while ((readLine = reader.read(chars)) != -1) {
//                System.out.print(new String(chars,0,readLine)+" ");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null){//避免空指针异常
                try {
                    fileInputStream.close();
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Test
    void redisListCommandTest(){
        ArrayList<String> studentList = new ArrayList<>();
        studentList.add("xiaohong");
        studentList.add("xiaoming");
        studentList.add("jack");
        for (int i = 0; i < studentList.size(); i++) {
            stringRedisTemplate.opsForList().rightPush("student", JSON.toJSONString(studentList.get(i)));
        }
        List<String> student = stringRedisTemplate.opsForList().range("student", 0, -1);
        for (int i = 0; i < student.size(); i++) {
            System.out.println(student.get(i));
        }
    }
    @Test
    void redisStringCommandTest(){
        stringRedisTemplate.opsForValue().set("test","jackson");
        String test = stringRedisTemplate.opsForValue().get("test");
        System.out.println(test);
        stringRedisTemplate.delete("test");
        String testResult = stringRedisTemplate.opsForValue().get("test");
        System.out.println(testResult);
    }
}
