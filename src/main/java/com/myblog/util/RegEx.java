package com.myblog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myblog.model.Word;


public class RegEx {
	
	public static Word toLevelWord(String line) {
		Word levelWord = new Word();
		// /匹配双字节字符(包括汉字在内)：[^x00-xff]
		String regEx = "([^x00-xff])\\s*([a-zA-Z()\\-\']+)";//匹配一个宽字符 空白(空格) 大小写字母或连字符号(Coca-Cola/ice-cream/living-room/t-shirt/up-to-date/x-ray) 单引号(o'clock),不匹配数字
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(line);
		if (matcher.find()) {
			// int gc = matcher.groupCount();
			// System.out.println("gc = " + gc);
			// 一般要求4794个单词（含中学已学词汇），表中不设标记；
			// 较高要求1601个单词，表中标记为★；
			// 更高要求1281个单词，表中标记为▲。
			String cet = matcher.group(1);
			String word = matcher.group(2);
//			中考18Level
//			高考35Level
//			四级46Level
//			考研55Level
//			六级64Level
//			String level = "cet4";
//			if (cet.equals("★")) {
//				level = "cet6";
//			} else if (cet.equals("▲")) {
//				level = "cet8";
//			} else {
//				level = "cet4";
//			}
			String level = "四级";
			if (cet.equals("★")) {
				level = "六级";
			} else if (cet.equals("▲")) {
				level = "更高要求";
//				word = "";
			} else {
				level = "四级";
			}
			levelWord.setSpelling(word.replaceAll("[()]", "")); // 删除()小括号
//			levelWord.setSpelling(word.replaceAll("[()]", "").toLowerCase()); // 删除()小括号
			levelWord.setLevel(level);
		}
		return levelWord;
	  }
	
	// ★ authoritative
	// ▲ austere
	// ★ authorize/-ise
	// ax(e)
	// ★ behavio(u)ral
	// airplane/aeroplane
	public static String[] catchWord(String line) {
		// String []ret ={"",""};
		String[] arr = new String[2];
		// /匹配双字节字符(包括汉字在内)：[^x00-xff]
		String regEx = "([^x00-xff])\\s*([a-zA-Z()]+)";
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(line);
		if (matcher.find()) {
			int gc = matcher.groupCount();
			// System.out.println("gc = " + gc);
			// 一般要求4794个单词（含中学已学词汇），表中不设标记；
			// 较高要求1601个单词，表中标记为★；
			// 更高要求1281个单词，表中标记为▲。
			String cet = matcher.group(1);
			String word = matcher.group(2);
			String level = "48";
			if (cet.equals("★")) {
				level = "64";
			} else if (cet.equals("▲")) {
				level = "77";
			} else {
				level = "48";
			}
			word = word.replaceAll("[()]", ""); // 删除()小括号
			// System.out.println("group 1-2:" +cet + "," + word);

			// for(int i = 0; i <= gc; i++) {
			// System.out.println("group " + i + " :" + matcher.group(i));
			// }
			arr[0] = word;
			arr[1] = level;
		}
		return arr;
	}

	// 获取4级单词/6级单词/大学要求所有单词
	// level:4/6/8
	public static String catchWord(String line, int level) {
		// /匹配双字节字符(包括汉字在内)：[^x00-xff]
		String regEx = "([^x00-xff])\\s*([a-zA-Z()]+)";
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(line);
		if (matcher.find()) {
			// int gc = matcher.groupCount();
			// System.out.println("gc = " + gc);
			// 一般要求4794个单词（含中学已学词汇），表中不设标记；
			// 较高要求1601个单词，表中标记为★；
			// 更高要求1281个单词，表中标记为▲。
			String cet = matcher.group(1);
			String word = matcher.group(2);
			if (level <= 4) {
				if (cet.equals("★")) {
				} else if (cet.equals("▲")) {
				} else {
					return word.replaceAll("[()]", ""); // 删除()小括号
				}
			} else if (level <= 6) {
				if (cet.equals("★")) {
					return word.replaceAll("[()]", ""); // 删除()小括号
				} else if (cet.equals("▲")) {
				} else {
					return word.replaceAll("[()]", ""); // 删除()小括号
				}
			} else {
				return word.replaceAll("[()]", ""); // 删除()小括
			}
		}
		return "";
	}

	// 从指定的(第一个匹配的)字符串，删除到行尾 ,String tag
	public static String removeTrail(String line,String tag) {
		return line.replaceAll(tag+".*$", "");
	}
	
	// 判断字符串中是否包含数字
	public static boolean containsNumber(String line) {
		String regex="[\\d]+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		return matcher.find();
	}
	
	
	public static void main(String[] args) {
		// String line = "★ behavio(u)ral";
		// String line = "★ authorize/-ise";
		// String line = "  ax(e) ";
		//String line = "  airplane/aeroplane(r)";
		//catchWord(line);
		
		//System.out.println("removeTrail=" + removeTrail(line,"/"));
		
		//System.out.println("replaceAll=" + line.replaceAll("[()\\d]", ""));
		
		//System.out.println("containsNumber=" + containsNumber(line));
	}

}
