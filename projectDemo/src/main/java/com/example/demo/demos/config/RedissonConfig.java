package com.example.demo.demos.config;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
    @Bean
    public Jedis jedis(){
        JedisPool jedisPool = new JedisPool("192.168.119.128", 6379);
        Jedis jedis = jedisPool.getResource();

        return jedis;
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
