package com.example.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.util.ListUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

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
    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
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
