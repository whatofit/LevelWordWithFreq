package com.level.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class File2Util {
	public static String FILE_ENCODING = "UTF-8";// CharsetName
	 private File2Util() {
	        throw new AssertionError();
	    }

   

    

	/**
	 * 将DOS/Windows格式的路径转换为UNIX/Linux格式的路径。
	 * 其实就是将路径中的"\"全部换为"/"，因为在某些情况下我们转换为这种方式比较方便，
	 * 某中程度上说"/"比"\"更适合作为路径分隔符，而且DOS/Windows也将它当作路径分隔符。
	 * 
	 * @param filePath
	 *            转换前的路径
	 * @return 转换后的路径
	 * @since 1.0
	 */
	public static String toUNIXpath(String filePath) {
		return filePath.replace('\\', '/');
	}

	/**
	 * 从文件名得到UNIX风格的文件绝对路径。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 对应的UNIX风格的文件路径
	 * @since 1.0
	 * @see #toUNIXpath(String filePath) toUNIXpath
	 */
	public static String getUNIXfilePath(String fileName) {
		File file = new File(fileName);
		return toUNIXpath(file.getAbsolutePath());
	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 * 
	 * @param fileName
	 *            文件名
	 * @return 文件名中的类型部分
	 * @since 1.0
	 */
	public static String getTypePart(String fileName) {
		int point = fileName.lastIndexOf('.');
		int length = fileName.length();
		if (point == -1 || point == length - 1) {
			return "";
		} else {
			return fileName.substring(point + 1, length);
		}
	}

	/**
	 * 得到文件的类型。 实际上就是得到文件名中最后一个“.”后面的部分。
	 * 
	 * @param file
	 *            文件
	 * @return 文件名中的类型部分
	 * @since 1.0
	 */
	public static String getFileType(File file) {
		return getTypePart(file.getName());
	}

	/**
	 * 得到相对路径。 文件名不是目录名的子节点时返回文件名。
	 * 
	 * @param pathName
	 *            目录名
	 * @param fileName
	 *            文件名
	 * @return 得到文件名相对于目录名的相对路径，目录下不存在该文件时返回文件名
	 * @since 1.0
	 */
	public static String getSubpath(String pathName, String fileName) {
		int index = fileName.indexOf(pathName);
		if (index != -1) {
			return fileName.substring(index + pathName.length() + 1);
		} else {
			return fileName;
		}
	}
	
	


//	public static String readFile(String _sFileName, String _sEncoding) throws Exception {
//		StringBuffer buffContent = null;
//		String sLine;
//
//		FileInputStream fis = null;
//		BufferedReader buffReader = null;
//		if (_sEncoding == null || "".equals(_sEncoding)) {
//			_sEncoding = FILE_ENCODING;
//		}
//
//		try {
//			fis = new FileInputStream(_sFileName);
//			buffReader = new BufferedReader(new InputStreamReader(fis, _sEncoding));
//			boolean zFirstLine = "UTF-8".equalsIgnoreCase(_sEncoding);
//			while ((sLine = buffReader.readLine()) != null) {
//				if (buffContent == null) {
//					buffContent = new StringBuffer();
//				} else {
//					buffContent.append("\n");
//				}
//				if (zFirstLine) {
//					sLine = removeBomHeaderIfExists(sLine);
//					zFirstLine = false;
//				}
//				buffContent.append(sLine);
//			} // end while
//			return (buffContent == null ? "" : buffContent.toString());
//		} catch (FileNotFoundException ex) {
//			throw new Exception("要读取的文件没有找到!", ex);
//		} catch (IOException ex) {
//			throw new Exception("读取文件时错误!", ex);
//		} finally {
//			// 增加异常时资源的释放
//			try {
//				if (buffReader != null)
//					buffReader.close();
//				if (fis != null)
//					fis.close();
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//		}
//	}

//	public static File writeFile(InputStream is, String path, boolean isOverride) throws Exception {
//		String sPath = extractFilePath(path);
//		if (!pathExists(sPath)) {
//			makeDir(sPath, true);
//		}
//
//		if (!isOverride && fileExists(path)) {
//			if (path.contains(".")) {
//				String suffix = path.substring(path.lastIndexOf("."));
//				String pre = path.substring(0, path.lastIndexOf("."));
//				path = pre + "_" + TimeUtils.getNowTime() + suffix;
//			} else {
//				path = path + "_" + TimeUtils.getNowTime();
//			}
//		}
//
//		FileOutputStream os = null;
//		File file = null;
//
//		try {
//			file = new File(path);
//			os = new FileOutputStream(file);
//			int byteCount = 0;
//			byte[] bytes = new byte[1024];
//
//			while ((byteCount = is.read(bytes)) != -1) {
//				os.write(bytes, 0, byteCount);
//			}
//			os.flush();
//
//			return file;
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new Exception("写文件错误", e);
//		} finally {
//			try {
//				if (os != null)
//					os.close();
//				if (is != null)
//					is.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public static File writeFile(String path, String content, String encoding, boolean isOverride) throws Exception {
//		if (TextUtils.isEmpty(encoding)) {
//			encoding = FILE_WRITING_ENCODING;
//		}
//		InputStream is = new ByteArrayInputStream(content.getBytes(encoding));
//		return writeFile(is, path, isOverride);
//	}

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
				System.out.println("找不到指定的文件！" + filename);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容操作出错" + filename);
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

			// InputStream in = ...;
			// CharsetDecoder decoder = StandardCharset.UTF_8.newDecoder();
			// decoder.onMalformedInput(CodingErrorAction.IGNORE);
			// Reader reader = new InputStreamReader(in, decoder);

			// return Files.readAllLines(sPath);
			Charset charset = Charset.defaultCharset(); // Try the default one first.
			return Files.readAllLines(sPath, charset);
			// return Files.readAllLines(sPath, StandardCharsets.UTF_8);
		} catch (Exception e) {
			System.out.println("读取文件内容操作出错:" + uri.toString() + "\r\nException Message=" + e);
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
		return readStringList(filename, "\\s+", "#");
	}

	public static List<List<String>> readStringList(String filename, String delimiter) {
		return readStringList(filename, delimiter, "#");
	}

	// 文件以行为单位，每行用delimiter(空白)字符定界(分隔)符，load成一个二维的字符串list
	public static List<List<String>> readStringList(String filename, String delimiter, String comment) {
		if (delimiter == null) {
			delimiter = "\\s+"; // 正则表达式：空白符号
		}
		if (comment == null) {
			// comment = "#";
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
			for (List<String> curLine : linesList) {
				String newLine = String.join(",", curLine);
				lineList.add(newLine);
			}
			// String charset = "GBK";//"UTF-8";"GB2312";"GBK";
			// Utils.saveFile(filename, body, charset);
			ResourceUtil.writerFile(filename, lineList, isAppend);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 将文本内容写入待保存的路径中。
	 * 
	 * @param filename
	 * @param body
	 * @param charsetName
	 *            "UTF-8";"GB2312";"GBK";
	 */
	public static boolean saveFile(String filename, String body, String charsetName) {
		try {
			Writer out = new OutputStreamWriter(new FileOutputStream(filename, false), charsetName);
			out.write(body);
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean saveFile(String filename, List<String> wordLines, String charsetName) {
		String body = wordLines.stream().collect(Collectors.joining("\r\n")).toString();
		return File2Util.saveFile(filename, body, charsetName);
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
			Files.write(Paths.get(filename), lines, isAppend ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
			// FileWriter fw = new FileWriter(filename, isAppend);
			// //String body = StringUtils.join(lines, "\r\n");
			// String body = lines.stream().collect(Collectors.joining("\r\n")).toString();
			// fw.write(body);
			// fw.write("\r\n");
			// fw.flush();
			// fw.close();
		} catch (IOException e) {
			System.out.println("IOException when write file: " + filename);
		}
	}

	public static void writerFile2(String filename, List<List<String>> lines, boolean isAppend) {
		String body = "";
		writerFile(filename, body, isAppend);
	}




}
