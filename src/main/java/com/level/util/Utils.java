package com.level.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

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

		// String mXmlWordFile = "./src/06169-corn.xml";
		// String body = FileUtil.readFile(mXmlWordFile);
		// System.out.println("--body---" + body);
		// // String body = "表示<尤美>的";
		// String ret = Utils.replaceAngleBrackets(body);
		// System.out.println("--ret---" + ret);

		// File f = new File(forderPath);
		// String[] files = f.list();
		// for(String a:files){
		// System.out.println(a);
		// }
	}

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
        File tempFile =new File(path.trim());
        String name = tempFile.getName();
        if (name.length() > 4) {
        	return name.substring(0, name.length() - 4);
        }

//		int index = path.lastIndexOf("/");
//		if (index != -1) {
//			// name = name.substring(name.lastIndexOf("\\") + 1, name.length());
//
//			return path.substring(index + 1, path.length() - 4);
//		}
		return path;
	}

	/**
	 * 分割文件名，不包含扩展名 "./vocabulary_ciba/00058-some.xml" 分割成：00058和some
	 */
	public static String[] splitFileName(String path) {
		String name = getFileName(path);
		if (Utils.isEmpty(name)) {
			return new String[] { "", "" };
		}
		if (name.contains("-")) {
			return name.split("-");
		}else {
			return new String[] { path, "" };
		}
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

	// 转换成Unicode如"\u810f\u7684"转换成："脏的"
	// {"1":{"percent":37,"sense":"\u810f\u7684"},"2":{"percent":27,"sense":"\u80ae\u810f\u7684"},"3":{"percent":15,"sense":"\u5f04\u810f"},"4":{"percent":8,"sense":"\u5351\u9119\u7684"},"5":{"percent":7,"sense":"\u4e0b\u6d41\u7684"},"6":{"percent":3,"sense":"\u73b7\u6c61"},"7":{"percent":2,"sense":"\u4e0d\u6b63\u5f53\u7684"},"8":{"percent":1,"sense":"\u8150\u8d25\u7684"}}
	// {"1":{"percent":37,"sense":"脏的"},"2":{"percent":27,"sense":"肮脏的"},"3":{"percent":15,"sense":"弄脏"},"4":{"percent":8,"sense":"卑鄙的"},"5":{"percent":7,"sense":"下流的"},"6":{"percent":3,"sense":"玷污"},"7":{"percent":2,"sense":"不正当的"},"8":{"percent":1,"sense":"腐败的"}}
	public static String convertUnicode(String ori) {
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
							throw new IllegalArgumentException("Malformed   \\uxxxx   encoding.");
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

	// 按value的大小进行排序
	public static List<String> SortMap(Map<String, String> oldmap, boolean isIncludeKey) {

		ArrayList<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(oldmap.entrySet());

		// Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
		// @Override
		// public int compare(Entry<String, String> o1, Entry<String, String> o2) {
		// return o2.getValue() - o1.getValue(); // 降序
		// }
		// });

		List<String> wordFreqList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if (isIncludeKey) {
				wordFreqList.add(list.get(i).getKey() + "\t" + list.get(i).getValue());
			} else {
				wordFreqList.add(list.get(i).getValue());
			}
			// System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
		}
		return wordFreqList;
	}
}
