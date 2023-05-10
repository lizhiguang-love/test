package com.example.thread;

public class InheritableThreadLocalhostDemo {
    public static void main(String[] args) {
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("threadLocal测试");
        inheritableThreadLocal.set("主线程变量");
        new Thread(()->{
            String s = threadLocal.get();
            String str = inheritableThreadLocal.get();
            System.out.println(str);
            System.out.println(s);
        }).start();
        System.out.println(inheritableThreadLocal.get());
        System.out.println(threadLocal.get());
    }
}
