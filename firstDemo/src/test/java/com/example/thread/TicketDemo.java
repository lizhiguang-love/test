package com.example.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicketDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 1000; i++) {
            executorService.submit(new ticketcount());
        }

        executorService.shutdown();

    }

}
class ticketcount implements Runnable{
    static int count=1000;
    @Override
    public void run() {
        count--;
        System.out.println("线程："+Thread.currentThread().getName()+" 剩余票数："+count);
    }
}
