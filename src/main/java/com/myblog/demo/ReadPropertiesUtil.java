package com.myblog.demo;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertiesUtil {

    public static void main(String[] args) {
//        File directory = new File("");//设定为当前文件夹
//        System.out.println(directory.getAbsolutePath());//获取绝对路径
//        System.out.println(directory.getPath()); //获得new File()时设定的路径
        //System.out.println(System.getProperties());
        
        /**
         * class.getClassLoader().getResourceAsStream($path), 其中$path默认是src源路径，maven项目一般配置了多个源路径
         * 例如：maven项目的源路径为：src/main/java，src/main/resources,src/test/java,src/test/resources,在此四个源
         * 路径下的文件可以直接写文件名即可读取
         */
        
        System.out.println(Thread.currentThread().getContextClassLoader().getResource("src/main/resources/freqOfWords.properties"));
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource("com/wpy/json/data.properties"));
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource("db.properties"));
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource("src/test/java/file1.properties"));
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource("file2.properties"));
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource("file3.properties"));
        
//        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//        System.out.println(classloader);
//        
//        if (classloader == null) {
//            Properties iframeproperties = new Properties();
//            classloader = iframeproperties.getClass().getClassLoader();
//        }
//        System.out.println(classloader);
        
//        try {
//            System.out.println(directory.getCanonicalPath());//获取标准的路径
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
