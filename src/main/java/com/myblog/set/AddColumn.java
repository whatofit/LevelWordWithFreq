package com.myblog.set;
//一个集合之中的内容不重复，唯一

//根据2个集合，添加(合并)一列
//比如集合1的每行如下：
//2	abandon	核	vt.离弃，丢弃；遗弃，抛弃；放弃
//5	able	基	a.有(能力、时间、知识等)做某事，有本事的
//5432	zone	核	n.地区,区域 v.分区,划分地带

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.myblog.Constant;
import com.myblog.model.Word;
import com.myblog.util.CfgUtil;
import com.myblog.util.RegEx;
import com.myblog.util.ResourceUtil;

//结合2的每行如下:
//able
//zoo

//1.读取列表1到list1，读取列表2到list2
//
public class AddColumn {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		// 1.读取文件路径/#被加数augend/summand:#加数addend
		String cfg_augend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_augend");
		String cfg_addend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_addend");
		String cfg_sum_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_sum_result");

		// 2.处理coca原文件集合为word list
		RegEx.spelling_Idx = 0;
		Map<String, String> augendMap = loadWordList(cfg_augend);
		RegEx.spelling_Idx = 0;
		Map<String, String> addendMap = loadWordList(cfg_addend);

		List<String> wordLines = new ArrayList<String> ();
		for (Iterator it = augendMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			String word = (String) entry.getKey();
			String augendLine =  (String) entry.getValue();
			String newLine = augendLine;
			if (addendMap.containsKey(word)) {
				newLine += "\t" + addendMap.get(word);
				addendMap.remove(word);
			}
			wordLines.add(newLine);
		}
		for (Iterator it = addendMap.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			String word = (String) entry.getKey();
			String addendLine =  (String) entry.getValue();
			String newLine = "\t\t\t\t"+addendLine;
			wordLines.add(newLine);
		}
		// 3.保存word list
		String cocaWordFile = Constant.PATH_RESOURCES + cfg_sum_result;
		ResourceUtil.writerFile(cocaWordFile, wordLines, false);
		long endTime = System.currentTimeMillis();
		System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
	}

	public static Map<String, String> loadWordList(String file) {
		System.out.println("parse word file: " + file);
		List<String> words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + file);
		Map<String, String> wordMap = words.parallelStream()
				.filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
				.map(RegEx::split2Word)
				.distinct() // 剔重
				// .collect(Collectors.toSet());
				.collect(Collectors.toMap(Word::getSpelling, Word::getSentences));
		System.out.println("unique words count: " + wordMap.size());
		return wordMap;
	}

}
