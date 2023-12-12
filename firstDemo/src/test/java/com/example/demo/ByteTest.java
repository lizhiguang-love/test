package com.example.demo;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ByteTest {
    public static void main(String[] args) {
        String str="蔡kk";
        
        String[] split ={"于","上官","欧阳","蔡","吴"};
        String splits="上官,欧阳,蔡";
        String inFirstChineseName="1";
        String inLastChineseName="";
        System.out.println(str.substring(1));
        if(splits.contains("官欧")){
            System.out.println(splits);
        }
        if(str.length()>2){
            for (String s : split) {
                if (str.substring(0,2).contains(s)){
                    inFirstChineseName=str.substring(0,s.length());
                    inLastChineseName=str.substring(s.length());
                    System.out.println(str.substring(0,s.length()));
                    System.out.println(str.substring(s.length()));
                }
            }
        }

        if (inFirstChineseName.equalsIgnoreCase("")){
            inFirstChineseName=str.substring(0,1);
            inLastChineseName=str.substring(1);
        }

        String strs="歐陽、太史、端木、上官、司馬、東方、獨孤、南宮、萬俟、聞人、夏侯、諸葛、尉遲、公羊、赫連、澹台、皇甫、宗政、濮陽、公冶、太叔、申屠、公孫、慕容、仲孫、鐘離、長孫、宇文、司徒、鮮於、司空、閭丘、子車、亓官、司寇、巫馬、公西、顓孫、壤駟、公良、漆雕、樂正、宰父、穀梁、 拓跋、夾穀、軒轅、令狐、段幹、百里、呼延、東郭、南門、羊舌、微生、公戶、公玉、公儀、梁丘、公仲、公上、公門、公山、公堅、左丘、公伯、西門、公祖、第五、公乘、貫丘、公皙、南榮、東裏、東宮、仲長、子書、子桑、即墨、達奚、褚師、吳銘" ;
        System.out.println(strs.replace("、",","));
        String unicode="\\u6b50\\u967d,\\u592a\\u53f2,\\u7aef\\u6728,\\u4e0a\\u5b98,\\u53f8\\u99ac,\\u6771\\u65b9,\\u7368\\u5b64,\\u5357\\u5bae,\\u842c\\u4fdf,\\u805e\\u4eba,\\u590f\\u4faf,\\u8af8\\u845b,\\u5c09\\u9072,\\u516c\\u7f8a,\\u8d6b\\u9023,\\u6fb9\\u53f0,\\u7687\\u752b,\\u5b97\\u653f,\\u6fee\\u967d,\\u516c\\u51b6,\\u592a\\u53d4,\\u7533\\u5c60,\\u516c\\u5b6b,\\u6155\\u5bb9,\\u4ef2\\u5b6b,\\u9418\\u96e2,\\u9577\\u5b6b,\\u5b87\\u6587,\\u53f8\\u5f92,\\u9bae\\u65bc,\\u53f8\\u7a7a,\\u95ad\\u4e18,\\u5b50\\u8eca,\\u4e93\\u5b98,\\u53f8\\u5bc7,\\u5deb\\u99ac,\\u516c\\u897f,\\u9853\\u5b6b,\\u58e4\\u99df,\\u516c\\u826f,\\u6f06\\u96d5,\\u6a02\\u6b63,\\u5bb0\\u7236,\\u7a40\\u6881, \\u62d3\\u8dcb,\\u593e\\u7a40,\\u8ed2\\u8f45,\\u4ee4\\u72d0,\\u6bb5\\u5e79,\\u767e\\u91cc,\\u547c\\u5ef6,\\u6771\\u90ed,\\u5357\\u9580,\\u7f8a\\u820c,\\u5fae\\u751f,\\u516c\\u6236,\\u516c\\u7389,\\u516c\\u5100,\\u6881\\u4e18,\\u516c\\u4ef2,\\u516c\\u4e0a,\\u516c\\u9580,\\u516c\\u5c71,\\u516c\\u5805,\\u5de6\\u4e18,\\u516c\\u4f2f,\\u897f\\u9580,\\u516c\\u7956,\\u7b2c\\u4e94,\\u516c\\u4e58,\\u8cab\\u4e18,\\u516c\\u7699,\\u5357\\u69ae,\\u6771\\u88cf,\\u6771\\u5bae,\\u4ef2\\u9577,\\u5b50\\u66f8,\\u5b50\\u6851,\\u5373\\u58a8,\\u9054\\u595a,\\u891a\\u5e2b,\\u5433\\u9298";
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(unicode);
        String chinese="";
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            unicode = unicode.replace(matcher.group(1), ch + "");
        }
        System.out.println(unicode);
        int a =0;
        a++;
        System.out.println(a);
//        do{
//            a++;
//            System.out.println(a);
//        }while (a==2);
//        System.out.println(++a);
    }
}
