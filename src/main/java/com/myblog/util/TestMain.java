package com.myblog.util;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Properties;

//java在gradle工程访问src/test/resources或者src/main/resources目录下的资源配置文件
public class  TestMain
{
    public static  void main(String args[]) throws URISyntaxException {
        System.out.println(new File(".").getAbsolutePath());
        Properties properties=new Properties();
        try {
            // properties.load(new FileInputStream("config.properties"));
            System.out.println(TestMain.class.getResource("/config.properties").toExternalForm());
            System.out.println(Thread.currentThread().getContextClassLoader().getResource("config.properties"));
            properties.load(TestMain.class.getResource("/config.properties").openStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
        String version=properties.getProperty("version");
        System.out.println(version);
        for(Map.Entry<Object,Object>  entry:properties.entrySet())
        {
            Object key=entry.getKey();
            Object value=entry.getValue();
            System.out.println(key+"="+value);
        }
    }

}