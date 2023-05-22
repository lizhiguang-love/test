package com.example.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.util.ListUtils;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.LockSupport;

public class ExcelTest {
    @Test
    public void simpleWrite(){
        String fileName = "D:\\"+"simpleWrite" + System.currentTimeMillis() + ".xlsx";
        System.out.println(fileName);
        EasyExcel.write(fileName,DemoData.class)
                .sheet("模板")
                .doWrite(()->{
                    return data();
                });
    }
    @Test
    public void simpleRead(){
        String fileName = "D:\\"+"simpleWrite1683103723615.xlsx";
//        EasyExcel.read(fileName,DemoData.class,new PageReadListener<DemoData>(dataList->{
//            for (DemoData demoData : dataList) {
//                System.out.println(demoData);
//            }
//        })).sheet().doRead();
        EasyExcel.read(fileName,DemoData.class,new DemoDataListener()).sheet().doRead();
    }
    @Test
    void test(){
        Vector<String> vector = new Vector<>(10);
        String[] a=new String[10];
        vector.add("1");
        vector.add("2");
        vector.add("3");
        a[0]="0";
        a[1]="1fdasdf";
        a[2]="2";
        int as=0;
        System.out.println(a[1].charAt(as          ++));
        System.out.println(as);

    }
    @Test
    void tests(){
        String str="hello";
        String str1=new String("hello");
        String str2=new String("hello");
        String str3=str2;
        String str4="he"+"llo";
        String str5=new String("he")+"llo";
        System.out.println(str1.equals(str2));
        System.out.println(str.equals(str1));
        System.out.println(str3==str2);
        System.out.println(str==str4);
        System.out.println(str5.toString());

    }
    private boolean tt(int a){
        if (a==1) return true;
        System.out.println("test");
        return false;
    }
    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        LockSupport.park();
        LockSupport.unpark(new Thread(()->{
            System.out.println("test");
        }));
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
