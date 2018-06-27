package com.myblog.rtf2txt.fmt;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.myblog.Constant;
import com.myblog.util.CfgUtil;
import com.myblog.util.FileUtil;
import com.myblog.util.ResourceUtil;
import com.myblog.util.Utils;

//把四六级词汇表整理3列:单词,衍生词s,四/六级
public class MergeCet46 {

	public MergeCet46() {
	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		// 1.读取单词集合路径
		String cfg_english_path = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_english_txt");
		// String forderPath = "E:/FanMingyou/The Economist";
		String englishTxtFile = Constant.PATH_RESOURCES + cfg_english_path;
		// 1.读取加载coca文件
		List<String> fileLines = ResourceUtil.readFileLines(englishTxtFile);
		// 2.处理原文件集合为word list
		List<String> wordLines = wordList2PairWithMerge(fileLines);
		// 3.保存word list
		String cfg_english_txt_result_path = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_english_txt_result");
		String txtWordFile = Constant.PATH_RESOURCES + cfg_english_txt_result_path;
		String body = wordLines.stream().collect(Collectors.joining("\r\n")).toString();
		String charset = "UTF-8";// "UTF-8";"GB2312";"GBK";
		FileUtil.writeFile(txtWordFile, body, false, charset);
		// ResourceUtil.writerFile(txtWordFile, wordLines, false);
		long endTime = System.currentTimeMillis();
		System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
	}

	// java统计一段英文中单词及个数/统计各个单词出现的次数
	public static List<String> wordList2PairWithMerge(List<String> fileLines) {
		List<String> wordLines = new ArrayList<String>();
		for (String curFileLine : fileLines) {// 待循环(可能重复)列表
			String cet = "";
			if (curFileLine.contains("★")) {
				curFileLine = curFileLine.replace("★", "");
				cet = "六";
			} else {
				cet = "四";
			}
			curFileLine = curFileLine.trim().replaceAll("\\s+", " ");// 多个空格替换成一个空格
			String[] fileLine = curFileLine.trim().split("\\s");// \t
			String strWord = fileLine[0];
			String subWords = "";// 衍生词:加了或去了词根词缀的词
			for (int i = 1; i < fileLine.length; i++) {
				if (subWords == "") {
					subWords = fileLine[i];
				} else {
					// subWords += "\r\n" + fileLine[i];
					subWords += ";" + fileLine[i];
				}
			}
			// wordLines.add(strWord+"\t"+subWords);
			// wordLines.add(cet+"\t"+strWord+"\t"+subWords);
			// wordLines.add(cet+"\t"+strWord+"\t\""+subWords+"\"");
			// wordLines.add(cet+","+strWord+",\""+subWords+"\"");
			// wordLines.add(strWord+"\t\""+subWords+"\""+"\t"+cet);
			wordLines.add(cet + "," + strWord + "," + subWords);
		}
		return wordLines;
	}

}
