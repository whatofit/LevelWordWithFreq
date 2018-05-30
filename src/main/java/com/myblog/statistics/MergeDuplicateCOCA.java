package com.myblog.statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.myblog.Constant;
import com.myblog.util.CfgUtil;
import com.myblog.util.RegEx;
import com.myblog.util.ResourceUtil;

public class MergeDuplicateCOCA {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		// 1.读取单词集合路径
		String cfg_englist_path = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_englist_txt");
		// String forderPath = "E:/FanMingyou/The Economist";
		String cocaFile = Constant.PATH_RESOURCES + cfg_englist_path;
		// 1.读取加载coca文件
		List<String> cocaFileLines = ResourceUtil.readFileLines(cocaFile);
		// 2.处理coca原文件集合为word list
		List<String> wordLines = new ArrayList<String>();
		// for (String curFileLine : cocaFileLines) {// 待循环(可能重复)列表
		// String[] fileLine = curFileLine.split("\t");
		// boolean isAdd = true;// is Add Or Update
		// for (int i = 0; i < wordLines.size(); i++) {
		// String curWordLine = wordLines.get(i);
		// String[] wordLine = curWordLine.split("\t");
		// if (fileLine[1].equalsIgnoreCase(wordLine[1])) {
		// isAdd = false;
		// String newWordLine = wordLine[0] + "/" + fileLine[0] + "\t" + wordLine[1] +
		// "\t" + wordLine[2] + "/"
		// + fileLine[2];
		// wordLines.set(i, newWordLine);
		// break;
		// }
		// }
		// if (isAdd) {
		// wordLines.add(curFileLine);// 添加到list末尾
		// }
		// }
		Map<String, String> mapWords = wordList2SetWithMerge(cocaFileLines);
		wordLines = SortMap(mapWords);
		// 3.保存word list
		String cfg_englist_txt_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_englist_txt_result");
		String cocaWordFile = Constant.PATH_RESOURCES + cfg_englist_txt_result;
		ResourceUtil.writerFile(cocaWordFile, wordLines, false);
		long endTime = System.currentTimeMillis();
		System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
	}

	// java统计一段英文中单词及个数/统计各个单词出现的次数
	public static Map<String, String> wordList2SetWithMerge(List<String> cocaFileLines) {
		Map<String, String> mapWords = new HashMap<String, String>();
		for (String curFileLine : cocaFileLines) {// 待循环(可能重复)列表
			String[] fileLine = curFileLine.split("\\s");// \t
			String strWord = fileLine[1];
			// 转为小写
			String key = strWord.toLowerCase();
			// String key = strWord;
			if (mapWords.containsKey(key)) {
				String oldWord = mapWords.get(key);
				String[] wordLine = oldWord.split("\\s");// \t
				String newWordLine = wordLine[0] + "/" + fileLine[0] + "\t" + wordLine[1];
				if (wordLine.length > 2) {
					newWordLine += "\t" + wordLine[2] + "/" + fileLine[2];
				}
				mapWords.put(key, newWordLine);
			} else {
				mapWords.put(key, curFileLine);
			}
		}

		return mapWords;
	}

	// 按频率的大小进行排序
	public static List<String> SortMap(Map<String, String> oldmap) {

		ArrayList<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(oldmap.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			@Override
			public int compare(Entry<String, String> obj1, Entry<String, String> obj2) {
				int o1 = RegEx.catchFirstNumber(obj1.getValue());
				int o2 = RegEx.catchFirstNumber(obj2.getValue());
				return o1 - o2; // 降序
			}
		});

		List<String> wordFreqList = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {
			wordFreqList.add(list.get(i).getValue());
			// System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
		}
		return wordFreqList;
	}
}
