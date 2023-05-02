package com.example.redisLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;

public class SingleRedisLockUtil {
    JedisPool jedisPool=new JedisPool("192.168.119.128",6379);
    //锁过期时间
    protected long internalLockLeaseTime=30000;
    //获取锁的超时时间
    private long timeout=999999;

    SetParams setParams=SetParams.setParams().nx().px(internalLockLeaseTime);

    /**
     * 加锁
     * @param lockkey 锁链
     * @param requestId 请求唯一标识
     * @return
     */
    public boolean tryLock(String lockkey,String requestId){
        String threadName = Thread.currentThread().getName();
        Jedis jedis = this.jedisPool.getResource();
        long start = System.currentTimeMillis();

        try {
            for (;;){
                String lockResult = jedis.set(lockkey, requestId, setParams);
                if ("OK".equals(lockResult)){
                    System.out.println(threadName+"获取锁成功");
                    return true;
                }
                //否则循环等待,在timeout时间内仍未获取到锁，则获取失败
                System.out.println(threadName+"获取锁失败，等待中....");
                long l = System.currentTimeMillis() - start;
                if (l>timeout){
                    return false;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            jedis.close();
        }
    }

    /**
     * 解锁
     * @param lockey
     * @param requestId
     * @return
     */
    public boolean releaseLock(String lockey,String requestId){
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName+"释放锁");
        Jedis jedis = this.jedisPool.getResource();
        String lua="if redis.call('get',KEYS[1])==ARGV[1] then"+
                "  return redis.call('del',KEYS[1])"+
                "else"+
                "   return 0  "+
                "end";
        try {
            Object result = jedis.eval(lua, Collections.singletonList(lockey),
                    Collections.singletonList(requestId));
            if ("1".equals(result.toString())){
                return true;
            }
            return false;
        }finally {
            jedis.close();
        }
    }

}
