/**
 * 
 */
package com.myblog.util;

import java.util.ArrayList;
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
		try {
			// Path resPath = Paths.get(new URI(Constant.PROJECT_BIN_DIR +
			// resourceFilename));
			// Path resPath = Paths.get(Constant.PROJECT_BIN_DIR +
			// resourceFilename);
			// return FileUtil.readFile(resPath.toAbsolutePath().toString());
			return FileUtil.readFile4(resourceFilename);
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
			return FileUtil.readFileLines(resourceFilename);
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
			return FileUtil.readStringList(resourceFilename);
		} catch (Exception e) {
			System.out.println("readStringList:" + e.toString());
		}
		return new ArrayList<List<String>>();
	}

	// 文件以行为单位，每行用空白字符分割，load成一个二维的字符串list
	public static List<List<String>> readStringList(String resourceFilename, String comment) {
		try {
			// Path resPath = Paths.get(new URI(Constant.PROJECT_BIN_DIR +
			// resourceFilename));
			// return
			// FileUtil.readStringList(resPath.toAbsolutePath().toString());
			return FileUtil.readStringList(resourceFilename, comment);
		} catch (Exception e) {
			System.out.println("readStringList:" + e.toString());
		}
		return new ArrayList<List<String>>();
	}

	/**
	 * 当isAppend是true时，把body添加到filename文件末尾 ，当isAppend时false时，覆盖或新建filename文件
	 */
	public static void writerFile(String resourceFilename, String body, boolean isAppend) {
		try {
			// Path resPath = Paths.get(new
			// URI(ClassLoader.getSystemResource("")));
			// Utils.writerFile(resPath.toAbsolutePath().toString(),body);
			Utils.writerFile(resourceFilename, body, isAppend);
		} catch (Exception e) {
			System.out.println("writerFile:" + e.toString());
		}
	}

	/**
	 * 当isAppend是true时，把body添加到filename文件末尾 ，当isAppend时false时，覆盖或新建filename文件
	 */
	public static void writerFile(String resourceFilename, List<String> lines, boolean isAppend) {
		try {
			// Path resPath = Paths.get(new
			// URI(ClassLoader.getSystemResource("")));
			// Utils.writerFile(resPath.toAbsolutePath().toString(),body);
			Utils.writerFile(resourceFilename, lines, isAppend);
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
			return Utils.deleteFile(resourceFilename);
		} catch (Exception e) {
			System.out.println("deleteFile:" + e.toString());
		}
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String mErrFileList = "/ErrFile.txt";
		String xmlWordFile = "00023-this.xml";
		writerFile(mErrFileList, xmlWordFile, true);
	}

}
