package com.myblog.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
     * */
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

    /** 把body添加到filename文件末尾 */
    public static void writerFileTest(String filename, String body) {
        FileWriter fw;
        try {
            // "C:/add2.txt"
            fw = new FileWriter(filename, true);
            fw.write(body);
            fw.write("\r\n");
            fw.flush();
            fw.close();
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

}
