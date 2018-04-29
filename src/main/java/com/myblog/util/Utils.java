package com.myblog.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    // 1，验证传入路径是否为正确的路径名(Windows系统，其他系统未使用)
    // 验证字符串是否为正确路径名的正则表达式
    // private static String matches = "[A-Za-z]:\\\\[^:?\"><*]*";

    // 通过 sPath.matches(matches) 方法的返回值判断是否正确
    // sPath 为路径字符串
    /** checkString是否为null或空字符串"" */
    public static boolean isEmpty(String str) {
        if (str == null || "".equalsIgnoreCase(str)) {
            return true;
        }
        return false;
    }

    /** 获取文件名称，不包含扩展名 */
    public static String getFileName(String path) {
        if (Utils.isEmpty(path)) {
            return "";
        }

        // String name =
        // "D:/Jobs/DevIDEs/workspace/FmyVocabulary/./vocabulary_ciba/00058-some.xml";
        // "./vocabulary_ciba/00058-some.xml"
        int index = path.lastIndexOf("/");
        if (index != -1) {
            // name = name.substring(name.lastIndexOf("\\") + 1, name.length());

            return path.substring(index + 1, path.length() - 4);
        }
        return path;
    }

    /**
     * 分割文件名，不包含扩展名 "./vocabulary_ciba/00058-some.xml" 分割成：00058和some
     */
    public static String[] splitFileName(String path) {
        if (Utils.isEmpty(path)) {
            return new String[] { "", "" };
        }

        // String name =
        // "D:/Jobs/DevIDEs/workspace/FmyVocabulary/./vocabulary_ciba/00058-some.xml";
        // "./vocabulary_ciba/00058-some.xml"
        int index = path.lastIndexOf("/");
        if (index != -1) {
            // name = name.substring(name.lastIndexOf("\\") + 1, name.length());

            return path.substring(index + 1, path.length() - 4).split("-");
        }
        return new String[] { path, "" };
    }

    /**
     * 判断字符串中是否含有一对尖括号中包含汉字，如果有，把尖括号替换为中括号
     * 
     * 光，光明；发光体；日光，黎明；<诗>视力，眼神； 光，光明；发光体；日光，黎明；[诗]视力，眼神；
     * 
     * @param str
     *            传入的字符窜
     * @return
     */
    // ^[\u2E80-\u9FFF]+$
    // 匹配所有东亚区的语言
    // ^[\u4E00-\u9FFF]+$
    // 匹配简体和繁体
    // ^[\u4E00-\u9FA5]+$
    // 匹配简体

    public static String replaceAngleBrackets(String body) {
        String regExp = "([<]{1}([\u0391-\uFFE5]+)[>]{1})";

        Pattern p = Pattern.compile(regExp);
        boolean result = false;
        do {
            Matcher m = p.matcher(body);
            result = m.find();
            // System.out.println(result);
            if (result) {
                // System.out.println(m.group(0));
                String matcher1 = m.group(1);
                // System.out.println(ma);
                String matcher2 = m.group(2);
                // System.out.println(ma2);
                String replacement = "[" + matcher2 + "]";// <(\w+)>
                body = body.replaceAll(matcher1, replacement);
                // System.out.println(body);
            }
        } while (result);
        return body;
    }

    /**
     * 将文本内容写入待保存的路径中。
     * 
     * @param filename
     * @param body
     * @param encode
     */
    public static boolean saveFile(String filename, String body, String encode) {
        try {
            Writer out = new OutputStreamWriter(new FileOutputStream(filename, false), encode);
            out.write(body);
            out.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * 当isAppend是true时，把body添加到filename文件末尾 ，当isAppend时false时，覆盖或新建filename文件
     */
    public static void writerFile(String filename, String body, boolean isAppend) {
        try {
            // File file = new File(filename);
            // OutputStream fos = new FileOutputStream(file);
            // BufferedOutputStream buff = new BufferedOutputStream(fos);
            // buff.write(body.getBytes());
            // buff.flush();
            // buff.close();
            // // fos.write(body.getBytes());
            // // fos.flush();
            // fos.close();

            // "C:/add2.txt"
            FileWriter fw = new FileWriter(filename, isAppend);
            fw.write(body);
            fw.write("\r\n");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println("IOException when write file: " + filename);
        }
    }

    /**
     * 当isAppend是true时，把body添加到filename文件末尾 ，当isAppend时false时，覆盖或新建filename文件
     */
    public static void writerFile(String filename, List<String> lines, boolean isAppend) {
        try {
            // File file = new File(filename);
            // OutputStream fos = new FileOutputStream(file);
            // BufferedOutputStream buff = new BufferedOutputStream(fos);
            // buff.write(body.getBytes());
            // buff.flush();
            // buff.close();
            // // fos.write(body.getBytes());
            // // fos.flush();
            // fos.close();

            // "C:/add2.txt"
            Files.write(Paths.get(filename), lines, isAppend?StandardOpenOption.APPEND:StandardOpenOption.CREATE);
//            FileWriter fw = new FileWriter(filename, isAppend);
//            //String body = StringUtils.join(lines, "\r\n");
//            String body = lines.stream().collect(Collectors.joining("\r\n")).toString();
//            fw.write(body);
//            fw.write("\r\n");
//            fw.flush();
//            fw.close();
        } catch (IOException e) {
            System.out.println("IOException when write file: " + filename);
        }
    }
    
    // 2，实现删除文件的方法
    /**
     * 删除单个文件
     * 
     * @param sPath
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    // 3，通用的文件夹或文件删除方法，直接调用此方法，即可实现删除文件夹或文件，包括文件夹下的所有文件
    /**
     * 根据路径删除指定的目录或文件，无论存在与否
     * 
     * @param sPath
     *            要删除的目录或文件
     * @return 删除成功返回 true，否则返回 false。
     */
    public static boolean DeleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else { // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }

    // 4，实现删除文件夹的方法，
    /**
     * 删除目录（文件夹）以及目录下的文件
     * 
     * @param sPath
     *            被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            } // 删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        // Utils hfc = new Utils();
        // String path = "D:\\Abc\\123\\Ab1";
        // // boolean result = hfc.CreateFolder(path);
        // // System.out.println(result);
        // path = "D:\\Abc\\124";
        // boolean result = hfc.DeleteFolder(path);
        // System.out.println(result);

        // String name = Utils.getFileName("./vocabulary_ciba/00058-some.xml");
        // System.out.println(name);
        //
        // String[] name2 =
        // Utils.splitFileName("./vocabulary_ciba/00058-some.xml");
        // System.out.println(name2[0] +"-----" + name2[1]);

        String mXmlWordFile = "./src/06169-corn.xml";
        String body = FileUtil.readFile(mXmlWordFile);
        System.out.println("--body---" + body);
        // String body = "表示<尤美>的";
        String ret = Utils.replaceAngleBrackets(body);
        System.out.println("--ret---" + ret);
    }

    //转换成Unicode如"\u810f\u7684"转换成："脏的"
    //{"1":{"percent":37,"sense":"\u810f\u7684"},"2":{"percent":27,"sense":"\u80ae\u810f\u7684"},"3":{"percent":15,"sense":"\u5f04\u810f"},"4":{"percent":8,"sense":"\u5351\u9119\u7684"},"5":{"percent":7,"sense":"\u4e0b\u6d41\u7684"},"6":{"percent":3,"sense":"\u73b7\u6c61"},"7":{"percent":2,"sense":"\u4e0d\u6b63\u5f53\u7684"},"8":{"percent":1,"sense":"\u8150\u8d25\u7684"}}
    //{"1":{"percent":37,"sense":"脏的"},"2":{"percent":27,"sense":"肮脏的"},"3":{"percent":15,"sense":"弄脏"},"4":{"percent":8,"sense":"卑鄙的"},"5":{"percent":7,"sense":"下流的"},"6":{"percent":3,"sense":"玷污"},"7":{"percent":2,"sense":"不正当的"},"8":{"percent":1,"sense":"腐败的"}}
    public static String convertUnicode(String ori){
        char aChar;
        int len = ori.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + aChar - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + aChar - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + aChar - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
 
        }
        return outBuffer.toString();
    }
}
