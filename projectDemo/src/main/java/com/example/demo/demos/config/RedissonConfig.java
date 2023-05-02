package com.example.demo.demos.config;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redissonClient(){
        RedissonClient redissonClient;
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.119.128:6379");
        redissonClient=Redisson.create(config);
        return redissonClient;
    }

    public RedissonRedLock redissonRedLock(String lockKey){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://192.168.119.128:6379");
        RedissonClient redissonClient = Redisson.create(config);
        RLock rLock = redissonClient.getLock(lockKey);

        RedissonRedLock redissonRedLock = new RedissonRedLock(rLock);
        return redissonRedLock;
    }
}
