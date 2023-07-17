package com.example.thread;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class CompletableFutureDemo {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("jd");
        list.add("tb");
        list.add("pdd");
        ArrayList<CompletableFuture<String>> futureArrayList = new ArrayList<>();

        for (String netName : list) {
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                return "平台：" + netName + "  价格：" + (new netMall(netName).getPrice());
            });
            futureArrayList.add(completableFuture);
        }
        long startTime = System.currentTimeMillis();
        for (CompletableFuture<String> stringCompletableFuture : futureArrayList) {
            System.out.println(stringCompletableFuture.join());
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时--->"+(endTime-startTime)+"毫秒");

    }
}
class netMall{
    private String mallName;
    public netMall(String mallName){
        this.mallName=mallName;
    }
    public double getPrice(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Math.random();
    }
}