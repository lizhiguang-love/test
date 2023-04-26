package com.example.demo;

import com.example.demo.demos.mapper.UserTestMapper;
import com.example.demo.demos.pojo.UserTest;
import com.example.thread.InsertTarget;
import com.example.thread.InsertTargetRunnable;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
class DemoApplicationTests {
    @Resource
    private UserTestMapper userTestMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Test
    void contextLoads() {
        List<UserTest> userTests = userTestMapper.selectList(null);
        System.out.println(userTests.get(0).toString());
    }

    @Test
    void redisTest(){
        System.out.println(stringRedisTemplate.opsForValue().get("test"));
    }
    @Test
    void testInsert(){
        ArrayList<UserTest> allUser = new ArrayList(5000);

        //生成size个UserInfo
        for (int i = 0; i < 5000; i++) {
            UserTest userInfo = new UserTest();
            userInfo.setName("test"+i);
            userInfo.setEmail("email"+i);
            userInfo.setAge(i);
            allUser.add(userInfo);
        }

        long  startTime = System.currentTimeMillis();
        for (int i = 0;i<5000;i++){
            userTestMapper.insert(allUser.get(i));
        }
        System.out.println("单线程for循环插入5000耗时:"+(System.currentTimeMillis()-startTime));

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
        ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 50, 1, TimeUnit.HOURS, new LinkedBlockingQueue());

        //每个线程处理的数据量
        final Integer dataPartionLength=(allUser.size()+50-1)/50;

        int downLanchSize=0;
        //将downLanchSize的值算出来
        if (allUser.size()%dataPartionLength==0){
            downLanchSize=allUser.size()/dataPartionLength;
        }else {
            downLanchSize=allUser.size()/dataPartionLength+1;
        }
        CountDownLatch countDownLatch = new CountDownLatch(downLanchSize);
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < downLanchSize; i++) {
            //每个线程均分需要处理的数据
            List<List<UserTest>> partition = Lists.partition(allUser, dataPartionLength);
            //调用实现了Callable的InsertTarget类，将countDownLatch,partion传进去
            InsertTarget insertTarget = new InsertTarget(partition.get(i), countDownLatch,userTestMapper);

            FutureTask futureTask = new FutureTask(insertTarget);
            //执行任务
            executor.execute(futureTask);

        }


        try {
            //保证每个线程完成后再完成计时
            countDownLatch.await();
            long endTime = System.currentTimeMillis();
            System.out.println("线程数为{}的线程池插入5000用时--->"+(endTime - startTime));
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
    @Test
    void CompleteFutureTest(){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 50, 1, TimeUnit.HOURS, new LinkedBlockingQueue());
//        CompletableFuture<Void> future = CompletableFuture.runAsync(new InsertTargetRunnable(), executor);
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            executor.execute(new InsertTargetRunnable());
            return "hello";
        });

        try {
            System.out.println(future1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    @Test
    void selectTest(){
        List<UserTest> userTests = userTestMapper.selectUserTest();
//        System.out.println(userTests.size());
        userTests.forEach(userTest->{
            System.out.println(userTest.getEmail());
        });
    }
    @Test
    void ListTest(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        List<List<Integer>> partition = Lists.partition(list, 2);
        System.out.println(partition.get(2).get(1));
    }

}
