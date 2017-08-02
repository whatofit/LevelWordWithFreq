/**
 * 
 */
package com.myblog.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.myblog.Constant;

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
        try {
            // Path resPath = Paths.get(new URI(Constant.PROJECT_BIN_DIR +
            // resourceFilename));
            // Path resPath = Paths.get(Constant.PROJECT_BIN_DIR +
            // resourceFilename);
            // return FileUtil.readFile(resPath.toAbsolutePath().toString());
            return FileUtil.readFile(Constant.PROJECT_BIN_DIR + resourceFilename);
        } catch (Exception e) {
            System.out.println("readFile:" + e.toString());
        }
        return "";
    }

    public static List<String> readFileLines(String resourceFilename) {
        try {
            // Path resPath = Paths.get(new URI(Constant.PROJECT_BIN_DIR +
            // resourceFilename));
            // Path resPath = Paths.get(Constant.PROJECT_BIN_DIR +
            // resourceFilename);
            // return
            // FileUtil.readFileLines(resPath.toAbsolutePath().toString());
            return FileUtil.readFileLines(Constant.PROJECT_BIN_DIR + resourceFilename);
        } catch (Exception e) {
            System.out.println("readFileLines:" + e.toString());
        }
        return Collections.emptyList();
    }

    // 文件以行为单位，每行用空白字符分割，load成一个二维的字符串list
    public static List<List<String>> readStringList(String resourceFilename) {
        try {
            // Path resPath = Paths.get(new URI(Constant.PROJECT_BIN_DIR +
            // resourceFilename));
            // return
            // FileUtil.readStringList(resPath.toAbsolutePath().toString());
            return FileUtil.readStringList(Constant.PROJECT_BIN_DIR + resourceFilename);
        } catch (Exception e) {
            System.out.println("readStringList:" + e.toString());
        }
        return new ArrayList<List<String>>();
    }

    /** 把body添加到filename文件末尾 */
    public static void writerFile(String resourceFilename, String body) {
        try {
            // Path resPath = Paths.get(new
            // URI(ClassLoader.getSystemResource("")));
            // Utils.writerFile(resPath.toAbsolutePath().toString(),body);
            Utils.writerFile(Constant.PROJECT_BIN_DIR + resourceFilename, body);
        } catch (Exception e) {
            System.out.println("writerFile:" + e.toString());
        }
    }

    /**
     * 删除单个文件
     * 
     * @param sPath
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String resourceFilename) {
        try {
            // Path resPath = Paths.get(new URI(Constant.PROJECT_BIN_DIR +
            // resourceFilename));
            // return Utils.deleteFile(resPath.toAbsolutePath().toString());
            return Utils.deleteFile(Constant.PROJECT_BIN_DIR + resourceFilename);
        } catch (Exception e) {
            System.out.println("deleteFile:" + e.toString());
        }
        return false;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String mErrFileList = "ErrFile.txt";
        String xmlWordFile = "00023-this.xml";
        writerFile(mErrFileList, xmlWordFile);
    }

}
