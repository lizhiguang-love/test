package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTest {
    public static void main(String[] args) {

        try {
            Date date1 = new Date();
            TimeUnit.SECONDS.sleep(1);
            Date date2 = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            long time1=date1.getTime();
            long time2=date2.getTime();
            long time3=24*60*60*1000;
            System.out.println((time2-time1)>time3);
            String format1 = format.format(date2);
            System.out.println(format1);
            if (false || true){
                System.out.println("test");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
