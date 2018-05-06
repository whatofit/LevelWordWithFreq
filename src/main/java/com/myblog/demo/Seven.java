package com.myblog.demo;

//编写一个英文词频统计的程序。统计时不区分大小写，忽略所有的数字和标点符号。要求能处理普通的txt文本和html文本。在处理html文本时，要求过滤掉所有的html标签
//分析：
//这个做的比较水，处理Html格式时只是把尖括号和角括号里的内容去掉了
//剩下的参考了一些别的网页，利用了正则表达式跟map

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.*;

public class Seven {
    static void countTextWords(String string){
        String stringArray[]=string.split("[^a-zA-Z]");
        
        final Map<String,Integer> map=new HashMap<String,Integer>();
        Integer count;
        for(int i=0;i<stringArray.length;i++){
            count=map.get(stringArray[i]);
            if(count==null){
                map.put(stringArray[i], 1);
            }else{
                map.put(stringArray[i], count+1);
            }
        }
        map.remove("");    //不知道为什么会有 什么值都不是的东西统计进去，这样可以去掉
        Set set=map.keySet();
        
        Integer sum=0,value;
        String key;
        for(Iterator iter=set.iterator();iter.hasNext();){   //先计算出总的单词数
            key=(String)iter.next();
            value=map.get(key);
            sum+=value;
        }
        for(Iterator iter=set.iterator();iter.hasNext();){
            key=(String)iter.next();
            value=map.get(key);
            float frequency=(float)value/sum;
            
            java.text.DecimalFormat df=new java.text.DecimalFormat("#0.000");
            System.out.println(key+":"+value+"  "+df.format(frequency));
        }

    }
    
    static void countHtmlWords(String string){
        string=string.replaceAll("<.*?>", "");
        countTextWords(string);
    }
    
    public static void main(String[] args) throws IOException {
        System.out.println("本程序统计txt或html文件的英文词频");
        System.out.println("注意：此程序不识别缩写词，会把缩写词以'为界当做两个单词处理！");
        System.out.println("请输入文件(绝对路径,包括后缀名):");
        Scanner reader=new Scanner(System.in);
        String name=reader.next();
        
        String context="";
        int c;

        FileReader fr=new FileReader(name);
        while((c=fr.read())!=-1){
            context+=(char)c;
        }
        context=context.toLowerCase();

        
        if(name.endsWith(".txt"))
            countTextWords(context);
        else if(name.endsWith(".html"))
            countHtmlWords(context);
        else
            System.out.println("不处理这种文件");
        reader.close();
        fr.close();
        
    }
}