package com.level.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

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

/**
 * File Utils

 */
public class FileUtil {

	public final static String FILE_EXTENSION_SEPARATOR = ".";
	// "UTF-8";"GB2312";"GBK";//字符编码(可解决中文乱码问题 ) //"GB2312"
	public final static String FILE_DEFAULT_ENCODING = "UTF-8"; 

	private FileUtil() {
		throw new AssertionError();
	}
	
	public static String readFile(String filePath, String charsetName) {
		try {
			return FileUtils.readFileToString(new File(filePath), charsetName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
//	/**
//	 * read file
//	 * 
//	 * @param filePath
//	 * @param charsetName
//	 *            The name of a supported {@link java.nio.charset.Charset
//	 *            </code>charset<code>}
//	 * @return if file not exist, return null, else return content of file
//	 * @throws RuntimeException
//	 *             if an error occurs while operator BufferedReader
//	 */
//	public static String readFile(String filePath, String charsetName) {
//		File file = new File(filePath);
//		StringBuilder fileContent = new StringBuilder("");
//		if (file == null || !file.isFile()) {
//			return "";
//		}
//
//		BufferedReader reader = null;
//		try {
//			InputStreamReader is = new InputStreamReader(new FileInputStream(filePath), charsetName);
//			reader = new BufferedReader(is);
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				if (!fileContent.toString().equals("")) {
//					fileContent.append("\r\n");
//				}
//				fileContent.append(line);
//			}
//			return fileContent.toString();
////			FileInputStream fis=new FileInputStream(filePath);
////	        BufferedInputStream bis=new BufferedInputStream(fis);
////	        InputStreamReader isr = new InputStreamReader(bis, "UTF-8");
////	        BufferedReader reader = new BufferedReader(isr);
//		} catch (IOException e) {
//			throw new RuntimeException("IOException occurred. ", e);
//		} finally {
//			// IOUtils.close(reader);
//			IOUtils.closeQuietly(reader);
//		}
//	}

	public static String readFile(String filePath) {
		return readFile(filePath, FILE_DEFAULT_ENCODING);
	}

	public static List<String> readFileLines(String filePath, String charsetName) {
	try {
		Path sPath = Paths.get(filePath);
		// CharsetDecoder decoder = StandardCharset.UTF_8.newDecoder();
		// decoder.onMalformedInput(CodingErrorAction.IGNORE);
		// Reader reader = new InputStreamReader(in, decoder);

		/// Charset charset = Charset.defaultCharset(); // Try the default one first.
		// Charset charset = Charset.forName("Utf-8");
		// StandardCharsets.UTF_8.UTF_8
		// return Files.readAllLines(sPath, StandardCharsets.UTF_8);
		Charset charset = Charset.forName(charsetName);
		return Files.readAllLines(sPath, charset);
	} catch (Exception e) {
		System.out.println("读取文件内容操作出错:" + filePath + "\r\nException Message=" + e);
		return Collections.emptyList();
	}
}
	
	public static List<String> readFileLines(String filePath) {
		return readFileLines(filePath, FILE_DEFAULT_ENCODING);
	}

	// 文件以行为单位，每行用delimiter(空白)字符定界(分隔)符，load成一个二维的字符串list
	public static List<List<String>> readStringList(String filename, String delimiter, String comment, String charsetName) {
		if (delimiter == null) {
			delimiter = "\\s+"; // 正则表达式：空白符号
		}
		if (comment == null) {
			// comment = "#";
		}
		if (charsetName == null) {
			charsetName = FILE_DEFAULT_ENCODING;
		}
		List<List<String>> stageLevelList = new ArrayList<List<String>>();
		try {
			FileInputStream fis = new FileInputStream(filename);
			BufferedReader dr = new BufferedReader(new InputStreamReader(fis,charsetName));
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
	public static List<List<String>> readStringList(String filename, String delimiter) {
		return readStringList(filename,delimiter,null,null);
	}
	
	public static List<List<String>> readStringList(String filename) {
		return readStringList(filename,null);
	}
		
	/**
	 * write file
	 * 
	 * @param filePath
	 * @param content
	 * @param append
	 *            is append, if true, write to the end of file, else clear content
	 *            of file and write into it
	 * @return return false if content is empty, true otherwise
	 * @throws RuntimeException
	 *             if an error occurs while operator FileWriter
	 */
	public static boolean writeFile(String filePath, String content,String charsetName, boolean append) {
		if (StringUtils.isEmpty(content)) {
			return false;
		}
		
		try {
			FileUtils.writeStringToFile(new File(filePath), content, charsetName, append);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return false;
	}
	/**
	 * write file, the string will be written to the begin of the file
	 * 
	 * @param filePath
	 * @param content
	 * @return
	 */
	public static boolean writeFile(String filePath, String content) {
		return writeFile(filePath, content, FILE_DEFAULT_ENCODING, false);
	}
	
	public static boolean writeFile(String filePath, String content, boolean append) {
		return writeFile(filePath, content, FILE_DEFAULT_ENCODING, append);
	}
	
	/**
	 * write file
	 * 
	 * @param filePath
	 * @param contentList
	 * @param append
	 *            is append, if true, write to the end of file, else clear content
	 *            of file and write into it
	 * @return return false if contentList is empty, true otherwise
	 * @throws RuntimeException
	 *             if an error occurs while operator FileWriter
	 */
	public static boolean writeFile(String filePath, String charsetName,List<String> contentList, boolean append) {
		if (contentList == null || contentList.size() == 0) {
			return false;
		}
		try {
			FileUtils.writeLines(new File(filePath), charsetName, contentList, "\r\n", append);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		return false;
	}



	/**
	 * write file, the string list will be written to the begin of the file
	 * 
	 * @param filePath
	 * @param contentList
	 * @return
	 */
	public static boolean writeFile(String filePath, List<String> contentList) {
		return writeFile(filePath, FILE_DEFAULT_ENCODING, contentList, false);
	}
//
//	/**
//	 * write file, the bytes will be written to the begin of the file
//	 * 
//	 * @param filePath
//	 * @param stream
//	 * @return
//	 * @see {@link #writeFile(String, InputStream, boolean)}
//	 */
//	public static boolean writeFile(String filePath, InputStream stream) {
//		return writeFile(filePath, stream, false);
//	}
//
//	/**
//	 * write file
//	 * 
//	 * @param file
//	 *            the file to be opened for writing.
//	 * @param stream
//	 *            the input stream
//	 * @param append
//	 *            if <code>true</code>, then bytes will be written to the end of the
//	 *            file rather than the beginning
//	 * @return return true
//	 * @throws RuntimeException
//	 *             if an error occurs while operator FileOutputStream
//	 */
//	public static boolean writeFile(String filePath, InputStream stream, boolean append) {
//		return writeFile(filePath != null ? new File(filePath) : null, stream, append);
//	}
//
//	/**
//	 * write file, the bytes will be written to the begin of the file
//	 * 
//	 * @param file
//	 * @param stream
//	 * @return
//	 * @see {@link #writeFile(File, InputStream, boolean)}
//	 */
//	public static boolean writeFile(File file, InputStream stream) {
//		return writeFile(file, stream, false);
//	}
//
//	/**
//	 * write file
//	 * 
//	 * @param file
//	 *            the file to be opened for writing.
//	 * @param stream
//	 *            the input stream
//	 * @param append
//	 *            if <code>true</code>, then bytes will be written to the end of the
//	 *            file rather than the beginning
//	 * @return return true
//	 * @throws RuntimeException
//	 *             if an error occurs while operator FileOutputStream
//	 */
//	public static boolean writeFile(File file, InputStream stream, boolean append) {
//		OutputStream o = null;
//		try {
//			o = new FileOutputStream(file, append);
//			byte data[] = new byte[1024];
//			int length = -1;
//			while ((length = stream.read(data)) != -1) {
//				o.write(data, 0, length);
//			}
//			o.flush();
//			return true;
//		} catch (FileNotFoundException e) {
//			throw new RuntimeException("FileNotFoundException occurred. ", e);
//		} catch (IOException e) {
//			throw new RuntimeException("IOException occurred. ", e);
//		} finally {
//			// IOUtils.close(o);
//			// IOUtils.close(stream);
//			IOUtils.closeQuietly(o);
//			IOUtils.closeQuietly(stream);
//		}
//	}
//
//	/**
//	 * move file
//	 * 
//	 * @param sourceFilePath
//	 * @param destFilePath
//	 */
//	public static void moveFile(String sourceFilePath, String destFilePath) {
//		if (StringUtils.isEmpty(sourceFilePath) || StringUtils.isEmpty(destFilePath)) {
//			throw new RuntimeException("Both sourceFilePath and destFilePath cannot be null.");
//		}
//		moveFile(new File(sourceFilePath), new File(destFilePath));
//	}
//
//	/**
//	 * move file
//	 * 
//	 * @param srcFile
//	 * @param destFile
//	 */
//	public static void moveFile(File srcFile, File destFile) {
//		boolean rename = srcFile.renameTo(destFile);
//		if (!rename) {
//			copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
//			deleteFile(srcFile.getAbsolutePath());
//		}
//	}
//
//	/**
//	 * copy file
//	 * 
//	 * @param sourceFilePath
//	 * @param destFilePath
//	 * @return
//	 * @throws RuntimeException
//	 *             if an error occurs while operator FileOutputStream
//	 */
//	public static boolean copyFile(String sourceFilePath, String destFilePath) {
//		InputStream inputStream = null;
//		try {
//			inputStream = new FileInputStream(sourceFilePath);
//		} catch (FileNotFoundException e) {
//			throw new RuntimeException("FileNotFoundException occurred. ", e);
//		}
//		return writeFile(destFilePath, inputStream);
//	}
//
//	// 拷贝文件
//	public static boolean copyFile(File sourceFile, File destFile) {
//		return copyFile(sourceFile.getAbsoluteFile(), destFile.getAbsoluteFile());
//	}
//	
//	
//	/**
//	 * read file to string list, a element of list is a line
//	 * 
//	 * @param filePath
//	 * @param charsetName
//	 *            The name of a supported {@link java.nio.charset.Charset
//	 *            </code>charset<code>}
//	 * @return if file not exist, return null, else return content of file
//	 * @throws RuntimeException
//	 *             if an error occurs while operator BufferedReader
//	 */
//	public static List<String> readFileToList(String filePath, String charsetName) {
//		File file = new File(filePath);
//		List<String> fileContent = new ArrayList<String>();
//		if (file == null || !file.isFile()) {
//			return null;
//		}
//
//		BufferedReader reader = null;
//		try {
//			InputStreamReader is = new InputStreamReader(new FileInputStream(file), charsetName);
//			reader = new BufferedReader(is);
//			String line = null;
//			while ((line = reader.readLine()) != null) {
//				fileContent.add(line);
//			}
//			return fileContent;
//		} catch (IOException e) {
//			throw new RuntimeException("IOException occurred. ", e);
//		} finally {
//			// IOUtils.close(reader);
//			IOUtils.closeQuietly(reader);
//		}
//	}
//	
//	/**
//	 * 从文件路径得到文件名。
//	 * 
//	 * @param filePath
//	 *            文件的路径，可以是相对路径也可以是绝对路径
//	 * @return 对应的文件名
//	 * @since 1.0
//	 */
//	public static String getFileName(String filePath) {
//		File file = new File(filePath);
//		return file.getName();
//	}
//
//	/**
//	 * 从文件名得到文件绝对路径。
//	 * 
//	 * @param fileName
//	 *            文件名
//	 * @return 对应的文件路径
//	 * @since 1.0
//	 */
//	public static String getFilePath(String fileName) {
//		File file = new File(fileName);
//		return file.getAbsolutePath();
//	}
//
//	public static String getFileExtension(String filePathName) {
//		String fileName =  getFileName(filePathName);
//		int extPos = fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
//		if (extPos == -1) {
//			return "";
//		}
//		return filePathName.substring(extPos + 1);
//	}
//	
////
////	/**
////	 * get file name from path, not include suffix
////	 * 
////	 * <pre>
////	 *      getFileNameWithoutExtension(null)               =   null
////	 *      getFileNameWithoutExtension("")                 =   ""
////	 *      getFileNameWithoutExtension("   ")              =   "   "
////	 *      getFileNameWithoutExtension("abc")              =   "abc"
////	 *      getFileNameWithoutExtension("a.mp3")            =   "a"
////	 *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
////	 *      getFileNameWithoutExtension("c:\\")              =   ""
////	 *      getFileNameWithoutExtension("c:\\a")             =   "a"
////	 *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
////	 *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
////	 *      getFileNameWithoutExtension("/home/admin")      =   "admin"
////	 *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
////	 * </pre>
////	 * 
////	 * @param filePath
////	 * @return file name from path, not include suffix
////	 * @see
////	 */
////	public static String getFileNameWithoutExtension(String filePath) {
////		if (StringUtils.isEmpty(filePath)) {
////			return filePath;
////		}
////
////		int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
////		int filePosi = filePath.lastIndexOf(File.separator);
////		if (filePosi == -1) {
////			return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
////		}
////		if (extenPosi == -1) {
////			return filePath.substring(filePosi + 1);
////		}
////		return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
////	}
//
////	/**
////	 * get file name from path, include suffix
////	 * 
////	 * <pre>
////	 *      getFileName(null)               =   null
////	 *      getFileName("")                 =   ""
////	 *      getFileName("   ")              =   "   "
////	 *      getFileName("a.mp3")            =   "a.mp3"
////	 *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
////	 *      getFileName("abc")              =   "abc"
////	 *      getFileName("c:\\")              =   ""
////	 *      getFileName("c:\\a")             =   "a"
////	 *      getFileName("c:\\a.b")           =   "a.b"
////	 *      getFileName("c:a.txt\\a")        =   "a"
////	 *      getFileName("/home/admin")      =   "admin"
////	 *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
////	 * </pre>
////	 * 
////	 * @param filePath
////	 * @return file name from path, include suffix
////	 */
////	public static String getFileName(String filePathName) {
////		if (StringUtils.isEmpty(filePathName)) {
////			return filePathName;
////		}
////        int nPos = filePathName.lastIndexOf('/');//File.separator
////        if (nPos < 0) {
////            nPos = filePathName.lastIndexOf('\\');
////        }
////		return (nPos == -1) ? filePathName : filePathName.substring(nPos + 1);
////	}
////
////	/**
////	 * get folder name from path
////	 * 
////	 * <pre>
////	 *      getFolderName(null)               =   null
////	 *      getFolderName("")                 =   ""
////	 *      getFolderName("   ")              =   ""
////	 *      getFolderName("a.mp3")            =   ""
////	 *      getFolderName("a.b.rmvb")         =   ""
////	 *      getFolderName("abc")              =   ""
////	 *      getFolderName("c:\\")              =   "c:"
////	 *      getFolderName("c:\\a")             =   "c:"
////	 *      getFolderName("c:\\a.b")           =   "c:"
////	 *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
////	 *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
////	 *      getFolderName("/home/admin")      =   "/home"
////	 *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
////	 * </pre>
////	 * 
////	 * @param filePath
////	 * @return
////	 */
////	public static String getFolderName(String filePathName) {
////		if (StringUtils.isEmpty(filePathName)) {
////			return filePathName;
////		}
////
////        int nPos = filePathName.lastIndexOf('/');//File.separator
////        if (nPos < 0) {
////            nPos = filePathName.lastIndexOf('\\');
////        }
////		return (nPos == -1) ? "" : filePathName.substring(0, nPos);
////	}
////
////	/**
////	 * get suffix of file from path
////	 * 
////	 * <pre>
////	 *      getFileExtension(null)               =   ""
////	 *      getFileExtension("")                 =   ""
////	 *      getFileExtension("   ")              =   "   "
////	 *      getFileExtension("a.mp3")            =   "mp3"
////	 *      getFileExtension("a.b.rmvb")         =   "rmvb"
////	 *      getFileExtension("abc")              =   ""
////	 *      getFileExtension("c:\\")              =   ""
////	 *      getFileExtension("c:\\a")             =   ""
////	 *      getFileExtension("c:\\a.b")           =   "b"
////	 *      getFileExtension("c:a.txt\\a")        =   ""
////	 *      getFileExtension("/home/admin")      =   ""
////	 *      getFileExtension("/home/admin/a.txt/b")  =   ""
////	 *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
////	 * </pre>
////	 * 
////	 * @param filePath
////	 * @return
////	 */
////	public static String getFileExtension(String filePathName) {
////		if (StringUtils.isBlank(filePathName)) {
////			return filePathName;
////		}
////
////		int extPos = filePathName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
////		int nPos = filePathName.lastIndexOf(File.separator);
////		if (extPos == -1) {
////			return "";
////		}
////		return (nPos >= extPos) ? "" : filePathName.substring(extPos + 1);
////	}
////
////	/**
////	 * Creates the directory named by the trailing filename of this file, including
////	 * the complete directory path required to create this directory. <br/>
////	 * <br/>
////	 * <ul>
////	 * <strong>Attentions:</strong>
////	 * <li>makeDirs("C:\\Users\\Trinea") can only create users folder</li>
////	 * <li>makeFolder("C:\\Users\\Trinea\\") can create Trinea folder</li>
////	 * </ul>
////	 * 
////	 * @param filePath
////	 * @return true if the necessary directories have been created or the target
////	 *         directory already exists, false one of the directories can not be
////	 *         created.
////	 *         <ul>
////	 *         <li>if {@link FileUtils#getFolderName(String)} return null, return
////	 *         false</li>
////	 *         <li>if target directory already exists, return true</li>
////	 *         <li>return {@link File#makeFolder}</li>
////	 *         </ul>
////	 */
////	public static boolean makeDirs(String filePath) {
////		String folderName = getFolderName(filePath);
////		if (StringUtils.isEmpty(folderName)) {
////			return false;
////		}
////
////		File folder = new File(folderName);
////		return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
////	}
////
////	/**
////	 * @param filePath
////	 * @return
////	 * @see #makeDirs(String)
////	 */
////	public static boolean makeFolders(String filePath) {
////		return makeDirs(filePath);
////	}
//
//	/**
//	 * Indicates if this file represents a file on the underlying file system.
//	 * 
//	 * @param filePath
//	 * @return
//	 */
//	public static boolean isFileExist(String filePath) {
//		if (StringUtils.isBlank(filePath)) {
//			return false;
//		}
//
//		File file = new File(filePath);
//		return (file.exists() && file.isFile());
//	}
//
//	/**
//	 * Indicates if this file represents a directory on the underlying file system.
//	 * 
//	 * @param directoryPath
//	 * @return
//	 */
//	public static boolean isFolderExist(String directoryPath) {
//		if (StringUtils.isBlank(directoryPath)) {
//			return false;
//		}
//
//		File dire = new File(directoryPath);
//		return (dire.exists() && dire.isDirectory());
//	}
//
////	/**
////	 * delete file or directory
////	 * <ul>
////	 * <li>if path is null or empty, return true</li>
////	 * <li>if path not exist, return true</li>
////	 * <li>if path exist, delete recursion. return true</li>
////	 * <ul>
////	 * 
////	 * @param path
////	 * @return
////	 */
////	public static boolean deleteFile(String path) {
////		if (StringUtils.isBlank(path)) {
////			return true;
////		}
////
////		File file = new File(path);
////		if (!file.exists()) {
////			return true;
////		}
////		if (file.isFile()) {
////			return file.delete();
////		}
////		if (!file.isDirectory()) {
////			return false;
////		}
////		for (File f : file.listFiles()) {
////			if (f.isFile()) {
////				f.delete();
////			} else if (f.isDirectory()) {
////				deleteFile(f.getAbsolutePath());
////			}
////		}
////		return file.delete();
////	}

	/**
	 * get file size
	 * <ul>
	 * <li>if path is null or empty, return -1</li>
	 * <li>if path exist and it is a file, return file size, else return -1</li>
	 * <ul>
	 * 
	 * @param path
	 * @return returns the length of this file in bytes. returns -1 if the file does
	 *         not exist.
	 */
	public static long getFileSize(String path) {
		if (StringUtils.isBlank(path)) {
			return -1;
		}

		File file = new File(path);
		return (file.exists() && file.isFile() ? file.length() : -1);
	}
	
	// ===========================================================
	/**
	 * 创建指定的目录。 如果指定的目录的父目录不存在则创建其目录书上所有需要的父目录。 <b>注意：可能会在返回false的时候创建部分父目录。</b>
	 * 
	 * @param file
	 *            要创建的目录
	 * @return 完全创建成功时返回true，否则返回false。
	 */
	public static boolean makeDirectory(File file, boolean _bCreateParentDir) {
		File parent = file.getParentFile();
		if (parent != null) {
			if (_bCreateParentDir) {
				return parent.mkdirs();// 如果父目录不存在，则创建所有必需的父目录
			}else {
				return parent.mkdir();// 如果父目录不存在，不做处理
			}
		}
		return false;
	}
	public static boolean makeDirectory(File file) {
		return makeDirectory(file,true);
	}
	
	public static boolean makeDirectory(String fileName) {
		return makeDirectory(new File(fileName),true);
	}

	/**
	 * 清空指定目录中的文件。 这个方法将尽可能删除所有的文件，但是只要有一个文件没有被删除都会返回false。
	 * 另外这个方法不会迭代删除，即不会删除子目录及其内容。
	 * 
	 * @param directory
	 *            要清空的目录
	 * @return 目录下的所有文件都被成功删除时返回true，否则返回false.
	 */
	public static boolean emptyDirectory(File directory) {
		boolean result = true;
		File[] entries = directory.listFiles();
		for (int i = 0; i < entries.length; i++) {
			if (!entries[i].delete()) {
				result = false;
			}
		}
		return result;
	}

	public static boolean emptyDirectory(String directoryName) {
		return emptyDirectory(new File(directoryName));
	}
	
	// ===========================================================

	public static boolean deleteFile(String filePath) {
		return deleteFile(new File(filePath), null);
	}

	public static boolean deleteFile(File file) {
		return deleteFile(file, null);
	}

	public static boolean deleteFile(String filePath, String suffix) {
		return deleteFile(new File(filePath), suffix);
	}

	/**
	 * delete File 递归遍历某个文件(夹)下的所有文件（包括子文件的文件） 当删除出现错误时，不会立即终止删除,会继续递归删除
	 * 
	 * @param file
	 *            文件或文件夹(目录)
	 * @param suffix
	 *            待处理的文件名后缀,传null或空字符串,则处理所有文件或文件夹
	 * @return boolean 删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(File file, String suffix) {
		if (file == null || !file.exists()) { // 判断是否存在：也可能不存在
			return false;
		}
		if (file.isFile()) {// 判断是否为文件：也可能是目录/文件夹
			if (suffix == null || suffix.trim().length() == 0) {
				return file.delete();// 直接删除文件
			} else if (file.getName().endsWith(suffix)) {
				return file.delete();// 直接删除文件
			} else {
				return true;// 跳过指定后缀的文件;没有删除任何文件;即也没有发生删除错误
			}
		} else {
			File[] files = file.listFiles();// entries
			if (files == null || files.length == 0) {
				return file.delete();// 删除空目录
			}
			boolean bRet = true;
			for (File subFile : files) {
				if (subFile.isDirectory()) {// 判断是否文件夹
					bRet = bRet && deleteFile(subFile, suffix);// 调用自身,操作子目录
				} else {
					if (suffix == null || suffix.trim().length() == 0) {
						bRet = bRet && subFile.delete();
					} else if (subFile.getName().endsWith(suffix)) {
						bRet = bRet && subFile.delete();
					} else {
						// 跳过指定后缀的文件;没有删除任何文件;即也没有发生删除错误
					}
				}
			}
			bRet = bRet && file.delete();// 删除空目录
			return bRet;
		}
		// return false;
	}

	// ===========================================================

	public static List<String> getFileList(String filePath) {
		return getFileList(new File(filePath), null);
	}

	public static List<String> getFileList(File file) {
		return getFileList(file, null);
	}

	public static List<String> getFileList(String filePath, String suffix) {
		return getFileList(new File(filePath), suffix);
	}

	/**
	 * traverse File 递归遍历某个文件(夹)下的所有文件（包括子文件的文件）
	 * 
	 * @param file
	 *            文件或文件夹(目录)
	 * @param suffix
	 *            待处理的文件名后缀,传null或空字符串,则处理所有文件或文件夹
	 * @return List<String>/List<File> 文件(路径)列表
	 */
	public static List<String> getFileList(File file, String suffix) {
		List<String> resultNameList = new ArrayList<String>();
		if (file == null || !file.exists()) { // 判断是否存在：也可能不存在
			return resultNameList;
		}

		if (file.isFile()) {// 判断是否为文件：也可能是目录/文件夹
			if (suffix == null || suffix.trim().length() == 0) {
				resultNameList.add(file.getPath());
			} else if (file.getName().endsWith(suffix)) {
				// file.getAbsolutePath()/file.getPath()/file.getCanonicalPath()
				resultNameList.add(file.getAbsolutePath());
			} else {
				// 跳过指定后缀的文件
			}
		} else {
			File[] files = file.listFiles();// entries
			if (files == null || files.length == 0) {
				return resultNameList;// 判断目录下是不是空的
			}
			for (File subFile : files) {
				if (subFile.isDirectory()) {// 判断是否文件夹
					resultNameList.addAll(getFileList(subFile, suffix));// 调用自身,查找子目录
				} else {
					if (suffix == null || suffix.trim().length() == 0) {
						resultNameList.add(subFile.getPath());
					} else if (subFile.getName().endsWith(suffix)) {
						resultNameList.add(subFile.getPath());
					} else {
						// 跳过指定后缀的文件
					}
				}
			}
		}
		return resultNameList;
	}

	  /**
     * 移除字符串中的BOM前缀
     *
     * @param _sLine 需要处理的字符串
     * @return 移除BOM后的字符串.
     */
    private static String removeBomHeaderIfExists(String _sLine) {
        if (_sLine == null) {
            return null;
        }
        String line = _sLine;
        if (line.length() > 0) {
            char ch = line.charAt(0);
            // 使用while是因为用一些工具看到过某些文件前几个字节都是0xfffe.
            // 0xfeff,0xfffe是字节序的不同处理.JVM中,一般是0xfeff
            while ((ch == 0xfeff || ch == 0xfffe)) {
                line = line.substring(1);
                if (line.length() == 0) {
                    break;
                }
                ch = line.charAt(0);
            }
        }
        return line;
    }
    
	// ===========================================================
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

		// String forderPath = "d:/MyDrivers";
		String forderPath = "E:\\tmp\\";
		// List<String> resultNameList = new ArrayList<String>();
		List<String> resultNameList = getFileList(forderPath, ".docx");
		System.out.println("--resultFileName:" + resultNameList);
	}
}