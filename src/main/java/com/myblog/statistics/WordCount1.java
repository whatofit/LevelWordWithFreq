//package com.myblog.statistics;
//
//import java.io.BufferedReader;  
//import java.io.FileNotFoundException;  
//import java.io.FileReader;  
//import java.io.IOException;  
//import java.util.HashMap;  
//import java.util.Iterator;  
//import java.util.StringTokenizer;  
//  
//public class WordCount1 {  
//    public static void main(String[] args) {  
//        //用HashMap存放<单词:词频>这样一个映射关系  
//        HashMap<String, Integer> hashMap=new HashMap<String, Integer>();  
//        //用正则表达式来过滤字符串中的所有标点符号  
//        String regex = "[【】、.。,\"!--;:?\'\\]]";   
//        try {  
//            //读取要处理的文件  
//            BufferedReader br=new BufferedReader(new FileReader("F:\\file80.txt"));  
//            String value;  
//            while((value=br.readLine())!=null){  
//                value=value.replaceAll(regex, " ");  
//                //使用StringTokenizer来分词(StringTokenizer详见JDK文档)  
//                StringTokenizer tokenizer = new StringTokenizer(value);  
//                while(tokenizer.hasMoreTokens()){  
//                    String word=tokenizer.nextToken();  
//                    if(!hashMap.containsKey(word)){    
//                        hashMap.put(word, new Integer(1));    
//                    }else{    
//                        int k=hashMap.get(word).intValue()+1;    
//                        hashMap.put(word, new Integer(k));    
//                    }    
//                }  
//            }  
//            //遍历HashMap,输出结果  
//            Iterator iterator=hashMap.keySet().iterator();    
//            while(iterator.hasNext()){    
//                String word=(String) iterator.next();    
//                System.out.println(word+":\t"+hashMap.get(word));    
//            }    
//        } catch (FileNotFoundException e) {  
//            e.printStackTrace();  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        }  
//    }  
//}  