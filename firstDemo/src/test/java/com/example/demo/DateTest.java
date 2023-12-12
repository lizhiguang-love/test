package com.example.demo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTest {
    public static void main(String[] args) {

            try {
                String da = "19700101";
                String das = new String("19700101");
                Date date = new Date();
                SimpleDateFormat dateFormats = new SimpleDateFormat("yyyy-MM-dd");
//                System.out.println(dateFormats.format(date));
//                System.out.println("123".equalsIgnoreCase(null));
                System.out.println(da.equals(das));
//                System.out.println(da == das);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (false || true){
                System.out.println("test");
            }


    }
}
