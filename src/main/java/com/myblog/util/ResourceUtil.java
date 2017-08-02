/**
 * 
 */
package com.myblog.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author FanMingyou
 *
 */
public class ResourceUtil {

    /**
     * 
     */
    public ResourceUtil() {
    }

    public static String readFile(String resourceFilename) {
        StringBuffer sb = new StringBuffer();
        try {
            InputStream is = ClassLoader.getSystemResourceAsStream(resourceFilename);
            if (is == null) {
                System.out.println("找不到指定的文件：" + resourceFilename);
                return sb.toString();
            }
            String encoding = "UTF-8"; // 字符编码(可解决中文乱码问题 )
            InputStreamReader read = new InputStreamReader(is, encoding);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTXT = "";
            while ((lineTXT = bufferedReader.readLine()) != null) {
                sb.append(lineTXT);
            }
            read.close();
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错：" + e.toString());
        }
        return sb.toString();
    }

    public static List<String> readFileLines(String resourceFilename) {
        try {
            URL url = ClassLoader.getSystemResource(resourceFilename);
            if (url == null) {
                System.out.println("readResourceFileLines,文件不存在:" + resourceFilename);
                return Collections.emptyList();
            }else{
                Path sPath = Paths.get(url.toURI());
                return Files.readAllLines(sPath);
            }
        } catch (Exception e) {
            System.out.println("readResourceFileLines,读取文件内容操作出错:" + e.toString());
            return Collections.emptyList();
        }
    }

    // 文件以行为单位，每行用空白字符分割，load成一个二维的字符串list
    public static List<List<String>> readStringList(String resourceFilename) {
        System.out.println("readResourceStringList,resourceFilename:" + resourceFilename);
        List<List<String>> stageLevelList = new ArrayList<List<String>>();
        try {
            InputStream is = ClassLoader.getSystemResourceAsStream(resourceFilename);
            if (is == null) {
                System.out.println("找不到指定的文件：" + resourceFilename);
                return stageLevelList;
            }
            BufferedReader dr = new BufferedReader(new InputStreamReader(is));
            String line = dr.readLine();
            while (line != null) {
                String[] split = line.split("\\s+");
                List<String> lineStage = new ArrayList<String>(Arrays.asList(split));
                stageLevelList.add(lineStage);
                line = dr.readLine();
            }
        } catch (Exception e) {
            System.out.println("readResourceStringList:" + e.toString());
        }
        return stageLevelList;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {

    }

}
