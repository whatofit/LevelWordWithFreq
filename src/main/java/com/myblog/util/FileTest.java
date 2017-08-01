package com.myblog.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import com.myblog.Constant;

public class FileTest {

    public static void main(String[] args) {
//        System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));
//        System.out.println(FileTest.class.getClassLoader().getResource(""));
//        System.out.println(ClassLoader.getSystemResource(""));
//        System.out.println(FileTest.class.getResource(""));
//        System.out.println(FileTest.class.getResource("/")); // Class文件所在路径
//        System.out.println(new File("/").getAbsolutePath());
//        System.out.println(System.getProperty("user.dir"));

            URL url = ClassLoader.getSystemResource(Constant.FILE_STAGE_WORDS_FILES);
            System.out.println(url);
            System.out.println(url.getFile());
            System.out.println(url.getPath());
    }
}
