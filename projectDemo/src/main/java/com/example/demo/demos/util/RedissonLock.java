package com.example.demo.demos.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedissonLock {
    @Resource
    private RedissonClient redissonClient;

    /**
     * 加锁
     * @param lockey
     * @return
     */
    public boolean addLock(String lockey){
        try {
            if (redissonClient == null){
                System.out.println("redisson client is null");
                return false;
            }
            RLock lock = redissonClient.getLock(lockey);
            //设置锁超时时间为10s，到期自动释放
//            lock.lock(10, TimeUnit.SECONDS);
            //获取锁的等待时间
            lock.tryLock(20,TimeUnit.SECONDS);
            System.out.println(Thread.currentThread().getName()+":   获取到锁");
            //加锁成功
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean releaseLock(String lockey){
        try {
            if (redissonClient == null){
                System.out.println("redisson client is null");
                return false;
            }
            RLock lock = redissonClient.getLock(lockey);
            lock.unlock();
            System.out.println(Thread.currentThread().getName()+":   释放锁");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
