/**
 * 
 */
package com.myblog.util;

import java.util.List;

/**
 * @author FanMingyou
 *
 */
public class ResourceUtil {

	public final static String FILE_DEFAULT_ENCODING = "UTF-8"; // 字符编码(可解决中文乱码问题 ) //"GB2312"

	/**
	 * 
	 */
	public ResourceUtil() {
	}

	public static String readFile(String resourceFilename) {
		return FileUtil.readFile(resourceFilename);
	}

	public static List<String> readFileLines(String resourceFilename) {
		return FileUtil.readFileLines(resourceFilename);
	}

	// 文件以行为单位，每行用空白字符分割，load成一个二维的字符串list
	public static List<List<String>> readStringList(String resourceFilename) {
		return FileUtil.readStringList(resourceFilename);
	}

	// 文件以行为单位，每行用空白字符分割，load成一个二维的字符串list
	public static List<List<String>> readStringList(String resourceFilename, String delimiter) {
		return FileUtil.readStringList(resourceFilename, delimiter);
	}

	/**
	 * 当isAppend是true时，把body添加到filename文件末尾 ，当isAppend时false时，覆盖或新建filename文件
	 */
	public static void writerFile(String resourceFilename, String body, boolean isAppend) {
		FileUtil.writeFile(resourceFilename, body, isAppend);
	}

	/**
	 * 当isAppend是true时，把body添加到filename文件末尾 ，当isAppend时false时，覆盖或新建filename文件
	 */
	public static void writerFile(String resourceFilename, List<String> lines, boolean isAppend) {
		FileUtil.writeFile(resourceFilename, FILE_DEFAULT_ENCODING, lines, isAppend);
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
			return FileUtil.deleteFile(resourceFilename);
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
