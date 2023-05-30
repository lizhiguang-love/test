package com.example.redisLock;


import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisLocalTest {

    public static void main(String[] args) {
        //模拟多个5个客户端
        for (int i=0;i<5;i++) {
            Thread thread = new Thread(new LockRunnable());
            thread.start();

        }

    }
    private static class LockRunnable implements Runnable{

        @Override
        public void run() {
            SingleRedisLockUtil redisLockUtil = new SingleRedisLockUtil();
            String uuid = UUID.randomUUID().toString();
            boolean lock = redisLockUtil.tryLock("lock", uuid);
            if (lock){
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            redisLockUtil.releaseLock("lock",uuid);

        }
    }
}
