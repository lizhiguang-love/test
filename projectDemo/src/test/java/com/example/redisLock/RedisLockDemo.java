package com.example.redisLock;

import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisLockDemo {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private Jedis jedis;
//    public RedisLockDemo(StringRedisTemplate stringRedisTemplate,Jedis jedis){
//        this.stringRedisTemplate=stringRedisTemplate;
//        this.jedis=jedis;
//    }
    public void test(){
        System.out.println("进入到方法中了======");
        String uuid = UUID.randomUUID().toString();
        try {
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent("uuid", uuid, 120, TimeUnit.SECONDS);
            System.out.println("进入到方法中了"+result);
            if (result){
                System.out.println(uuid+"--->处理业务5秒钟");
            }else {
                System.out.println(uuid+"--->获取锁失败");
            }
        } finally {
            String script = "if redis.call('get',KEYS[1]) == ARGV[1] " +
                    "then " +
                    "return redis.call('del',KEYS[1]) " +
                    "else " +
                    "   return 0 " +
                    "end";
            Object eval = jedis.eval(script, Collections.singletonList("uuid"),
                    Collections.singletonList(uuid));
            if (eval.toString().equals("1")){
                System.out.println("------del redis lock ok....");
            }else {
                System.out.println("------del redis lock error....");
            }
        }


    }
}
