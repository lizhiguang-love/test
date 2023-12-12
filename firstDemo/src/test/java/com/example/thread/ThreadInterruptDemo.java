package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadInterruptDemo {
    static volatile boolean flag=true;
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.submit(() -> {

        });
        Thread t1 = new Thread(() -> {
            while (flag){
                System.out.println("正在执行");
                if (Thread.currentThread().isInterrupted()){
                    flag=false;
                    System.out.println("线程已被中断--->"+Thread.currentThread().isInterrupted());
                    break;
                }
            }

        }, "t1");
        t1.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            t1.interrupt();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
