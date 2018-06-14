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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/*
java提供了多种文件写入的方式，效率上各有异同，
基本上可以分为如下三大类：字节流输出、字符流输出、内存文件映射输出。
前两种又可以分为带buffer及不带buffer。

7. 实验结论
1、基本上，五种写入方式的时延从小到大排序为：FileChannel<BufferedOutputStream<FileOutputStream<BufferedWriter<FileWriter;从表1、图1；可以看出；且该规律在小文件写入的情景下，更为明显，图2可以看出；
2、在同样文件大小写入的场景中，通常意义上带buffer的字节流输入/字符流输入比不带buffer的对应流效率要高；
3、各个写入方式的jvm cpu和内存使用情况大致相当，从图2和图3可以看出；
4、文件达到一定大小后( fileSize >=1.5G )，FileChannel的时延变得很大且不稳定，从图1最右边可以看出；同时，物理内存的使用量基本和写入文件大小相当，从图4可以看出；原因在于FileChannel使用MappedByteBuffer写入，这个buffer是direct buffer，直接操作物理内存写入，故而造成物理内存消耗严重。
5、小文件写入的场景下（1M左右），FileChannel有些大材小用了，效率上反而没有字节流效率高。
综上，我们可以得到几条有价值的使用经验：
1、小文件（几M的文件）写入时，使用常规的io输入就行，最优选择是BufferedOutputStream，没有必要使用nio的FileChannel；
2、大文件（fileSize > 1G，这是个经验值，需要根据具体环境具体分析）写入时，使用FileChannel需要小心物理内存的瓶颈带来的写入效率低下，可以考虑使用分段写入的方式（TODO：后续实验给出）；
3、其他场景下，如果效率优先的考虑，则优先选择FileChannel写入文件。
*/
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
        return readStringList(filename,"\\s+","#");
    }
    public static List<List<String>> readStringList(String filename, String delimiter) {
        return readStringList(filename, delimiter,"#");
    }
    // 文件以行为单位，每行用delimiter(空白)字符定界(分隔)符，load成一个二维的字符串list
    public static List<List<String>> readStringList(String filename, String delimiter,String comment) {
    	if (delimiter == null) {
    		delimiter = "\\s+"; //正则表达式：空白符号
    	} 
    	if (comment == null) {
    		//comment = "#";
    	} 
        List<List<String>> stageLevelList = new ArrayList<List<String>>();
        try {
            FileInputStream fis = new FileInputStream(filename);
            BufferedReader dr = new BufferedReader(new InputStreamReader(fis));
            String line = dr.readLine();
            while (line != null) {
            	if (!"".equals(line.trim()) && (comment == null || !line.trim().startsWith(comment))) { // "#"
	                String[] split = line.split(delimiter);
	                List<String> lineStage = new ArrayList<String>(Arrays.asList(split));
	                stageLevelList.add(lineStage);
	                line = dr.readLine();
            	}
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stageLevelList;
    }

    // 文件以行为单位，write一个二维的字符串list
    public static boolean WriteStringList(String filename, List<List<String>> linesList, boolean isAppend) {
        List<String> lineList = new ArrayList<String>();
        try {
        	for ( List<String> curLine:linesList) {
        		String newLine =  String.join(",", curLine); 
        		lineList.add(newLine);
    		}
        	//String charset = "GBK";//"UTF-8";"GB2312";"GBK";
            //Utils.saveFile(filename, body, charset);
            ResourceUtil.writerFile(filename, lineList, isAppend);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
