package com.example.demo;

import java.util.HashMap;

public class ConditionDemo {
    public static void main(String[] args) {
        if (true || true){
            System.out.println("test");
        }
        HashMap<String, String> map = new HashMap<>();

        System.out.println(map.get("tt"));
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("1",1);
        HashMap<String,Integer> maps=hashMap;
        maps.put("2",3);
        System.out.println(hashMap.get("2"));
    }
}
