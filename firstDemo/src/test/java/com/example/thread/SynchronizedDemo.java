package com.example.thread;

import java.util.concurrent.TimeUnit;

public class SynchronizedDemo {
    public static void main(String[] args) {
        Phone p1 = new Phone();
        Phone p2 = new Phone();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p1.sendEmail();
        }).start();


        new Thread(()->{
            p1.sendMessage();
        }).start();

    }
}
class Phone{
     synchronized void sendEmail(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("send email...");
        System.out.println(Thread.currentThread().getName()+"--->"+"线程000");

    }

    synchronized void sendMessage(){
        System.out.println("send message....");
        System.out.println(Thread.currentThread().getName()+"--->"+"线程111");
     }
}