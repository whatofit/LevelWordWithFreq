//package com.myblog.rtf2txt.fmt;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import com.myblog.Constant;
//import com.myblog.model.Word;
//import com.myblog.util.CfgUtil;
//import com.myblog.util.FileUtil;
//import com.myblog.util.RegEx;
//import com.myblog.util.ResourceUtil;
//import com.myblog.util.Utils;
//
//public class Merge2Cet {
//
//	public static void main(String[] args) {
//		long startTime = System.currentTimeMillis();
//		// 1.读取文件路径/#被加数augend/summand:#加数addend
//		String cfg_augend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_augend");
//		String cfg_addend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_addend");
//		String cfg_sum_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_sum_result");
//
//		// 2.读取两个原文件集合
//		List<List<String>> augendFileLines = FileUtil.readStringList(Constant.PATH_RESOURCES + cfg_augend, ",");
//		List<List<String>> addendFileLines = FileUtil.readStringList(Constant.PATH_RESOURCES + cfg_addend, "\\s");
//
//		// 3.处理文件集合
//		List<List<String>> wordLines = merge2Cet46(augendFileLines, addendFileLines);
//
//		// 4.保存word list
//		String wordFile = Constant.PATH_RESOURCES + cfg_sum_result;
//		FileUtil.WriteStringList(wordFile, wordLines, false);
//		long endTime = System.currentTimeMillis();
//		System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
//	}
//
//	// 把单词合并到CET4/CET6的tree列表中
//	public static List<List<String>> merge2Cet46(List<List<String>> augendLines, List<List<String>> addendLines) {
//		List<List<String>> unmatchList = new ArrayList<List<String>>();
//		for (int i = 0; i < addendLines.size(); i++) {// 待循环(可能重复)列表
//			List<String> curAddendLine = addendLines.get(i);
//			boolean bFound = false;
//			for (int j = 0; j < augendLines.size(); j++) {// 待循环(可能重复)列表
//				List<String> curAugendLine = augendLines.get(j);
//				if (isFoundWord(curAddendLine, curAugendLine)) {
//					bFound = true;
//					curAugendLine.add(curAddendLine.get(0));// 添加1列
//					curAugendLine.add(curAddendLine.get(1));// 添加1列
//					augendLines.set(j, curAugendLine);// 更新到目标list里
//					break;
//				}
//			}
//			if (!bFound) {
//				unmatchList.add(curAddendLine);
//			}
//		}
//		augendLines.addAll(unmatchList);
//		return augendLines;
//	}
//
//	public static boolean isFoundWord(List<String> curAddendLine, List<String> curAugendLine) {
//		String wordAddend = RegEx.removeBrackets(curAugendLine.get(1));
//		if (wordAddend.equals(RegEx.removeBrackets(curAddendLine.get(0)))) {
//			return true;
//		} else {
//			String wordsCell = curAugendLine.get(2).replaceAll("\"", "");
//			String words[] = wordsCell.replaceAll("\"", "").split(",");
//			for (int i = 0; words !=null && i < words.length; i++) {
//				if (wordAddend.equals(RegEx.removeBrackets(words[i]))) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//}
