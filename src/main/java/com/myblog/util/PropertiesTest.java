package com.myblog.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Properties;

public class PropertiesTest {
    public static void main(String[] args) { 
        Properties prop = new OrderedProperties();     
        try{
            //读取属性文件config.properties
            InputStreamReader in = new InputStreamReader (new FileInputStream("src/input/config.properties"), "UTF-8");
            prop.load(in);     ///加载属性列表
            Iterator<String> it=prop.stringPropertyNames().iterator();
            while(it.hasNext()){
                String key=it.next();
                System.out.println(key+":"+prop.getProperty(key));
            }
            in.close();
            
            ///保存属性到b.properties文件
            OutputStreamWriter oFile = new OutputStreamWriter(new FileOutputStream("out.properties", true),"UTF-8");  //true表示追加打开
            prop.setProperty("phone", "10086");
            prop.store(oFile, "The New properties file");
            oFile.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    } 
}
