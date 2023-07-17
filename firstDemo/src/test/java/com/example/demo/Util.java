package com.example.demo;


import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class Util {
    private static volatile int a=10;
    static int num=0;
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"运行");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }

            }).start();
        }
        try {
            countDownLatch.await();
            System.out.println("子线程运行结束。。。。。");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    static void arrayTest(){
        ArrayList<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        Long[] array = list.toArray(new Long[list.size()]);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

}
