package com.myblog.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileUtil {

    public static String readFile2(String filename) {
        StringBuffer sb = new StringBuffer();
        try {
            FileInputStream f = new FileInputStream(filename);
            InputStreamReader isr = new InputStreamReader(f);
            System.out.println("isr.getEncoding() =" + isr.getEncoding());
            BufferedReader dr = new BufferedReader(isr);
            String line = dr.readLine();
            while (line != null) {
                sb.append(new String(line.getBytes("GBK")));// gb2312
                line = dr.readLine();
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // "d:/sql.txt"
    public static String readtxt(String filename) {
        BufferedReader br;
        String str = "";
        try {
            br = new BufferedReader(new FileReader(filename));
            String r = br.readLine();
            while (r != null) {
                str += r;
                r = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String readFile(String filename) {
        StringBuffer sb = new StringBuffer();
        try {
            String encoding = "UTF-8"; // 字符编码(可解决中文乱码问题 )
            File file = new File(filename);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTXT = "";
                while ((lineTXT = bufferedReader.readLine()) != null) {
                    sb.append(lineTXT);
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件！");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错");
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String readFile3(String filename) {
        try {
            String encoding = "UTF-8"; // 字符编码(可解决中文乱码问题 ) //"GB2312"
            File file = new File(filename);
            if (file.isFile() && file.exists()) {
                FileInputStream in = new FileInputStream(file);
                int fileSize = in.available(); // size为文件长度 ，这里一次性读完
                byte[] buffer = new byte[fileSize];
                in.read(buffer);
                in.close();
                return new String(buffer, encoding);
            } else {
                System.out.println("找不到指定的文件！"+filename);
            }
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错"+filename);
            e.printStackTrace();
        }
        return "";
    }

    public static String readFile4(String filename) {
        try {
            String encoding = "UTF-8"; // 字符编码(可解决中文乱码问题 ) //"GB2312"
            File file = new File(filename);
            if (file.isFile() && file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                FileChannel fc = fis.getChannel();
                ByteBuffer bb = ByteBuffer.allocate(new Long(file.length()).intValue());
                // fc向buffer中读入数据
                fc.read(bb);
                bb.flip();
                String str = new String(bb.array(), encoding);
                fc.close();
                fis.close();
                return str;
            } else {
                System.out.println("找不到指定的文件！");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错");
            e.printStackTrace();
        }
        return "";
    }

    public static List<String> readFileLines(String uri) {
        try {
            Path sPath = Paths.get(uri);
            // System.out.println("sPath:" + sPath);
            
//            InputStream in = ...;
//            CharsetDecoder decoder = StandardCharset.UTF_8.newDecoder();
//            decoder.onMalformedInput(CodingErrorAction.IGNORE);
//            Reader reader = new InputStreamReader(in, decoder);
            
            //return Files.readAllLines(sPath);
            Charset charset = Charset.defaultCharset(); //Try the default one first.
            return Files.readAllLines(sPath, charset);
            //return Files.readAllLines(sPath, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错:" + uri.toString() +"\r\nException Message="+e);
            return Collections.emptyList();
        }
    }

    public static List<String> readFileLines(URI uri) {
        try {
            return Files.readAllLines(Paths.get(uri));
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错:" + uri.toString());
            return Collections.emptyList();
        }
    }

    public static List<String> readFileLines(URL url) {
        try {
            return Files.readAllLines(Paths.get(url.toURI()));
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错:" + url.toString());
            return Collections.emptyList();
        }
    }

    // 文件以行为单位，每行用空白字符分割，load成一个二维的字符串list
    public static List<List<String>> readStringList(String filename) {
        List<List<String>> stageLevelList = new ArrayList<List<String>>();
        try {
            FileInputStream fis = new FileInputStream(filename);
            BufferedReader dr = new BufferedReader(new InputStreamReader(fis));
            String line = dr.readLine();
            while (line != null) {
                String[] split = line.split("\\s+");
                List<String> lineStage = new ArrayList<String>(Arrays.asList(split));
                stageLevelList.add(lineStage);
                line = dr.readLine();
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stageLevelList;
    }

    // 文件以行为单位，每行用空白字符分割，load成一个二维的字符串list
    public static List<List<String>> readStringList(String filename, String comment) {
        List<List<String>> stageLevelList = new ArrayList<List<String>>();
        try {
            FileInputStream fis = new FileInputStream(filename);
            BufferedReader dr = new BufferedReader(new InputStreamReader(fis));
            String line = dr.readLine();
            while (line != null) {
                if (!line.trim().startsWith(comment) && !"".equals(line.trim())) { // "#"
                    String[] split = line.split("\\s+");
                    List<String> lineStage = new ArrayList<String>(Arrays.asList(split));
                    stageLevelList.add(lineStage);
                }
                line = dr.readLine();
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stageLevelList;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // String filename = "e:/00835-wish.xml";
        // .getClass().
        // ClassLoader classLoader = FileUtil.class.getClassLoader();
        // File file = new File(classLoader.getResource(filename).getFile());

        // String filename = "." + File.separator + "freqOfWords.properties";
        // String filename = "freqOfWords.properties";

        // File configFile = new File(Constant.FILE_FREQ_OF_WORDS);
        // System.out.println("--getAbsolutePath:" +
        // configFile.getAbsolutePath());
        // String ret = FileUtil.readFile(Constant.FILE_FREQ_OF_WORDS);
        // System.out.println("--ret---" + ret);

        System.out.println(System.getProperty("user.dir"));// user.dir指定了当前的路径
    }
}
