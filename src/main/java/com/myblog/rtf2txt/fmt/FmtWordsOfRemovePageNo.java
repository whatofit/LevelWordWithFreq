package com.myblog.rtf2txt.fmt;

import java.util.ArrayList;
import java.util.List;

import com.myblog.Constant;
import com.myblog.util.CfgUtil;
import com.myblog.util.RegEx;
import com.myblog.util.ResourceUtil;

public class FmtWordsOfRemovePageNo {

	public static void main(String[] args) {
		doFmtRemovePageNo();
	}

	public static void doFmtRemovePageNo() {
		long startTime = System.currentTimeMillis();
		// 1.读取单词集合路径
		String cfg_english_path = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_english_txt");
		// String forderPath = "E:/FanMingyou/The Economist";
		String englishTxtFile = Constant.PATH_RESOURCES + cfg_english_path;
		// 2.加载/读取原文件line集合
		List<String> fileLines = ResourceUtil.readFileLines(englishTxtFile);
		// 3.处理文件集合:格式化/替换
		List<String> wordLines = fmtRemovePageNo(fileLines);
		// 3.保存word list
		String cfg_english_txt_result_path = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_english_txt_result");
		String txtWordFile = Constant.PATH_RESOURCES + cfg_english_txt_result_path;
		ResourceUtil.writerFile(txtWordFile, wordLines, false);
		long endTime = System.currentTimeMillis();
		System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
	}

	public static List<String> fmtRemovePageNo(List<String> fileLines) {
		List<String> wordList = new ArrayList<String>();
		for (String curFileLine : fileLines) {
			if (!curFileLine.contains("或")) {
				curFileLine = curFileLine.replaceAll("[\\u4e00-\\u9fa5]*", "");//删除汉字
			}
			if (curFileLine.contains("&")) {
				curFileLine = curFileLine.replaceAll("\\s*&\\s*", "&");//删除&两边的空格
			}
			if (curFileLine.contains("pl ")) {
				curFileLine = curFileLine.replaceAll("pl ", "pl.");//复数
			}
			curFileLine = curFileLine.replaceAll("  ", " ");//重复空格
			// "^\\d+$"//非负整数（正整数 + 0）
			if (!curFileLine.trim().isEmpty() && !RegEx.isNumber(curFileLine.trim())
					&& curFileLine.trim().length() > 1) { //移除ABCDEFG字母排序分类
				wordList.add(curFileLine.trim());
			}
		}
		// List<String> wordLines = new ArrayList<String>();
		for (int i = wordList.size() - 1; i > 0; i--) {// 循环到i大于0，即循环到1
			String previousLine = wordList.get(i - 1);
			String curWordLine = wordList.get(i);
			if (curWordLine.trim().startsWith("(") || (curWordLine.contains(")") && !curWordLine.contains("("))) {
				wordList.remove(i);
				wordList.set(i - 1, previousLine.trim() + curWordLine.trim());
				i--;
			}
		}
		
		List<String> wordLines = new ArrayList<String>();
		for (String curWordLine : wordList) {
			wordLines.add(RegEx.fmtNCEEWordLine(curWordLine.trim()));
		}
		return wordLines;
	}
}
