package com.myblog.set;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.myblog.Constant;
import com.myblog.model.Word;
import com.myblog.util.CfgUtil;
import com.myblog.util.RegEx;
import com.myblog.util.ResourceUtil;
import com.myblog.util.Utils;

//文件以csv
//1.读以CET(包括其衍生词)与其他Level的单词列表,主单词在第二列(下标1),衍生词(下标2),总列数M
//2.待合并level的单词列表,主单词在默认在第2列(下标1),无衍生词,总列数默认为2
public class MergeLevelWrod2CET {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		// 1.读取文件路径/#被加数augend/summand:#加数addend
		String cfg_augend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_augend");
		String cfg_addend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_addend");
		String cfg_sum_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_sum_result");

		RegEx.spelling_Idx = 1;
		Map<String, String> augendMap = loadWordList(cfg_augend);
		RegEx.spelling_Idx = 2;
		Map<String, String> addendMap = loadWordList(cfg_addend);
		int augendColumnCnt = getFileMaxColumnCnt(cfg_augend);
		// int addendColumnCnt = getFileColumnCnt(cfg_addend);
		System.out.println("augendColumnCnt=" + augendColumnCnt);

		// List<String> wordLines = new ArrayList<String>();
		// Map<String, String> wordMap = new HashMap<String, String>();
		boolean isContinue = true;
		while (isContinue) {
			isContinue = false;
			for (Iterator<Entry<String, String>> it = augendMap.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, String> entry = it.next();
				String augendKeyWord = (String) entry.getKey();
				String augendMapValue = (String) entry.getValue();
				// System.out.println("augendKeyWord \t augendMapValue=" +
				// augendKeyWord+"\t"+augendMapValue);
				// colo(u)r单词在考研单词词义的备注中
				if (addendMap.containsKey(augendKeyWord)) {// 1.若在addendMap的key中找到augendWord
					// 修改Map中key的Value:增加列数
					augendMap.put(augendKeyWord, augendMapValue + "," + addendMap.get(augendKeyWord));
					addendMap.remove(augendKeyWord);
					isContinue = true;
				} else {
					String[] augendField = augendMapValue.trim().split(",");
					if (augendField != null && augendField.length > 2) {// CET有衍生词
						// System.out.println("augendMapValue,field=" + Arrays.toString(augendField));
						String derivedWords = augendField[2];
						if (augendField.length > 3) {
							derivedWords +=";" + augendField[3];
						}
						if (!"".equals(derivedWords.trim())) {
							String[] derWords = derivedWords.split(";");// .replaceAll("\"", "") //分开CET衍生词
							for (int i = 0; derWords != null && i < derWords.length; i++) {// 循环CET衍生词
								String derivedWord = RegEx.removeBrackets(derWords[i]);// 规范化衍生词
								if (addendMap.containsKey(derivedWord)) {// 2.若在addendMap的key中找到augendWord的衍生词derivedWord
									String addendLine = addendMap.get(derivedWord);
									String newLine = "";
									int curLineColCnt = getLineColumnCnt(augendMapValue);
									if (curLineColCnt <= augendColumnCnt) {
										newLine = augendMapValue + "," + addendLine;// 第一次','拼接
									} else {
										String[] addendField = addendLine.trim().split(","); // 后续;拼接
										newLine = augendMapValue + ";" + addendField[RegEx.spelling_Idx];
									}
									augendMap.put(augendKeyWord, newLine);
									augendMapValue = augendMap.get(augendKeyWord);// key已变化，重新取key
									addendMap.remove(derivedWord);
									isContinue = true;
								}
							}
						}
					}
				}
				if (addendMap.size() == 0) {
					break;
				}
			}
		}
		Map<String, String> wordMap = new HashMap<String, String>();
		for (Iterator<Entry<String, String>> it = addendMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = it.next();
			String word = (String) entry.getKey();
			String addendLine = (String) entry.getValue();
			String[] field = addendLine.trim().split(",");
			// 前面是四六级+word+衍生word三列+研纲+多个都逗号分隔的词汇
			String newLine = "无," + field[RegEx.spelling_Idx] + joinEmptyColumn(augendColumnCnt - 1) + addendLine;
			augendMap.put(word, newLine);
			wordMap.put(word, newLine);
		}
		List<String> wordLines = Utils.SortMap(augendMap, false);
		// 3.保存word list
		String wordFile = Constant.PATH_RESOURCES + cfg_sum_result;
		ResourceUtil.writerFile(wordFile, wordLines, false);
		ResourceUtil.writerFile(Constant.PATH_RESOURCES + "/cfg_sum_result2.csv", Utils.SortMap(wordMap, false), false);

		long endTime = System.currentTimeMillis();
		System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
	}

	public static Map<String, String> loadWordList(String file) {
		System.out.println("parse word file: " + file);
		List<String> words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + file);
		Map<String, String> wordMap = words.parallelStream()
				.filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim())).map(RegEx::split2Word2)
				.distinct() // 剔重
				// .collect(Collectors.toSet());
				.collect(Collectors.toMap(Word::getSpelling, Word::getSentences));
		System.out.println("unique words count: " + wordMap.size());
		return wordMap;
	}

	public static int getFileMaxColumnCnt(String file) {
		System.out.println("getFileMaxColumnCnt,word file: " + file);
		List<String> words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + file);
		int nMaxColCnt = 0;
		for (String curLine : words) {// 待循环(可能重复)列表
			String[] columns = curLine.split(",");
			if (columns.length > nMaxColCnt) {
				nMaxColCnt = columns.length;
				// System.out.println("getFileMaxColumnCnt,nMaxColCnt: " + nMaxColCnt);
			}
		}
		return nMaxColCnt;
	}

	public static int getLineColumnCnt(String line) {
		// System.out.println("getLineColumnCnt,line: " + line);
		if (line != null) {
			String[] columns = line.trim().split(",");
			return columns.length;
		}
		return 0;
	}

	public static String joinEmptyColumn(int columnCnt) {
		// System.out.println("joinEmptyColumn,columnCnt: " + columnCnt);
		// String columns[] = new String[columnCnt];
		// return StringUtils.join(columns, ",");
		String line = "";
		for (int i = 0; i < columnCnt; i++) {
			line = line + ",";
		}
		return "".equals(line)?",":line;
	}
}
