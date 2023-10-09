package com.example.demo;


import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class Util {
    private static volatile int a=10;
    static int count=0;

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                for (int j = 0; j < 10; j++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                        count++;
                        atomicInteger.getAndIncrement();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
            System.out.println("count: "+count);
            System.out.println("atomicInteger: "+atomicInteger);
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
