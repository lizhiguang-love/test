package com.example.demo;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class StringDemo {
    public static void main(String[] args) {
        String text="hello world";
        String num="000000";

        System.out.println(text.indexOf("h"));
        System.out.println(text.substring(text.indexOf("l")));
        Vector<String> vector = new Vector<>();
        vector.addElement("321");
        vector.addElement("test");
        vector=new Vector<String>();
        if("str".equals("str") || "123".equals("123") && ("456".equals("456") || "789".equals("789"))){
            System.out.println("呵呵");
        }
    }
    static String test(){
        int i=1/0;
        try {

        } catch (Exception e) {
            System.out.println("哈哈");
            e.printStackTrace();
        }

        return "str";

    }
}
