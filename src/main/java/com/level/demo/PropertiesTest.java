package com.level.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Properties;

import com.level.util.OrderedProperties;

public class PropertiesTest {
    public static void main(String[] args) { 
        Properties prop = new OrderedProperties();     
        try{
            //读取属性文件config.properties
            InputStreamReader in = new InputStreamReader (new FileInputStream("src/main/resources/config.properties"), "UTF-8");
            prop.load(in);     ///加载属性列表
            Iterator<String> it=prop.stringPropertyNames().iterator();
            while(it.hasNext()){
                String key=it.next();
                System.out.println(key+":"+prop.getProperty(key));
            }
            in.close();
            
            System.out.println("1、phone：" + prop.getProperty("phone")) ;
            System.out.println("2、subtraction2：" + prop.getProperty("subtraction1")) ;
            System.out.println("3、subtraction3：" + prop.getProperty("subtraction2")) ;
            
            ///保存属性到b.properties文件
            OutputStreamWriter oFile = new OutputStreamWriter(new FileOutputStream("src/main/resources/config.properties", true),"UTF-8");  //true表示追加打开
            prop.setProperty("phone", "10086");
            prop.store(oFile, "The New properties file");
            oFile.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    } 
}
