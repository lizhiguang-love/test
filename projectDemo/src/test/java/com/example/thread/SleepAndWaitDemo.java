package com.example.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class SleepAndWaitDemo {
    public static void main(String[] args) {
        Object obj = new Object();
        new Thread(()->{
            synchronized (obj){
                System.out.println("先进的t1线程");
                LockSupport.park();
                System.out.println(Thread.currentThread().getName()+" com in.....");
            }
        },"t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            synchronized (obj){
                System.out.println("后进的t2线程");
                obj.notify();
                System.out.println(Thread.currentThread().getName()+" com in.....");

            }
        },"t2").start();
    }
}
