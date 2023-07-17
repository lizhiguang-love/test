package com.example.thread;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureTest {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("tb");
        list.add("jd");
        list.add("pdd");
        ArrayList<CompletableFuture<String>> futureArrayList = new ArrayList<>();
        for (String str : list) {
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                shopping(str);
                return "hello->"+str;
            });
            futureArrayList.add(completableFuture);
        }
        long startTime = System.currentTimeMillis();
        for (CompletableFuture<String> completableFuture : futureArrayList) {
            System.out.println(completableFuture.join());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时--->"+(endTime-startTime)+"毫秒");

    }

    public static void shopping(String shoppingName){
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println(shoppingName+"购物2s");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
