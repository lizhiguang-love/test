package com.example.demo;

public class StringDemo {
    public static void main(String[] args) {
        String text="hello world";
        System.out.println(text.indexOf("h"));
        System.out.println(text.substring(text.indexOf("l")));
        try {
            String strs=test();
            System.out.println(strs);
        } catch (Exception e) {
            System.out.println("呵呵");
            e.printStackTrace();
        }
    }
    static String test(){

        try {
            int i=1/0;
        } catch (Exception e) {
            System.out.println("哈哈");
            e.printStackTrace();
        }

        return "str";
    }
}
