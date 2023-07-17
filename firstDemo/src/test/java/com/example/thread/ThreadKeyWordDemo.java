package com.example.thread;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class ThreadKeyWordDemo {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private static  int num=1;
    @Test
    void threadLocalTest(){
        new Thread(()->{
            threadLocal.set("本地变量1");
            print("thread1");

        }).start();
        new Thread(()->{
            threadLocal.set("本地变量2");
            print("thread2");
        }).start();
    }
    public static void print(String str){
        System.out.println(str+":"+threadLocal.get());
    }
    @Test
    void lockSuportTest(){
        Thread t1 = new Thread(() -> {
            LockSupport.park();
            System.out.println(Thread.currentThread().getName() + "  come in...");
        }, "t1");
        t1.start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            LockSupport.unpark(t1);
            System.out.println(Thread.currentThread().getName()+"  come in...");
        },"t2").start();
    }
    @Test
    void inheritableThreadLocalTest(){
        m1();
    }

    synchronized void m1(){
        System.out.println("m1 come in...");
        m2();
    }
    synchronized void m2(){
        System.out.println("m2 come in...");
        m3();
    }
    synchronized void m3(){
        System.out.println("m3 come in...");
    }

}
