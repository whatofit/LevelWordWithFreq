package com.myblog.statistics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.myblog.Constant;
import com.myblog.model.Word;
import com.myblog.util.ResourceUtil;

/**
 * 把文章中的所有单词按照单词词频排序
 * 
 * @author FanMingyou
 *
 */
public class WordStatistics {

	public WordStatistics() {
	}

	public static void main(String[] args) throws IOException {
		// String fileName = "WordStatistics.txt";// 文件路径
		// // String formfileName =
		// //
		// "E:/workspace_4.6.3_LevelWord_2017-07-26/LevelWordWithFreq/src/main/resources/countwords.txt";
		// System.out.println(fileName);
		// String body = ResourceUtil.readFile(fileName);
		// Map<String, Integer> map = countWords(body);
		// List<Map.Entry<String, Integer>> infoIds = sort(map);
		// output(infoIds);
		// // output2(map);

		// getAllWord();
		getAllWord2();
		// getDistinctWord();
	}

	// java统计一段英文中单词及个数/统计各个单词出现的次数
	public static Map<String, Integer> countWords(String text) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		Pattern pn = Pattern.compile("\\b[a-zA-Z-]+\\b");// 正则表达式,匹配一个单词边界，也就是指单词和空格(标点符号)间的位置
		// Pattern pn = Pattern.compile("[^a-zA-Z-]+");// 正则表达式,匹配一个非单词
		Matcher mr = pn.matcher(text);
		while (mr.find()) {
			String strWord = mr.group();
			// 转为小写
			// String key = strWord.toLowerCase();
			String key = strWord;
			if (map.containsKey(key)) {
				map.put(key, map.get(key) + 1);
			} else {
				map.put(key, 1);
			}
		}
		return map;
	}

	// 排序
	public static List<Map.Entry<String, Integer>> sort(Map<String, Integer> map) {
		List<Map.Entry<String, Integer>> infoIds = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		return infoIds;
	}

	// 输出
	public static void output(List<Map.Entry<String, Integer>> infoIds) {
		int totalCnt = 0;
		for (int i = 0; i < infoIds.size(); i++) { // 输出
			Entry<String, Integer> id = infoIds.get(i);
			totalCnt += id.getValue();
			System.out.println("第" + (i + 1) + "行,个数" + id.getValue() + ":" + id.getKey());
		}
		System.out.println("单词总数:" + totalCnt);
	}

	// 输出2
	public static void output2(Map<String, Integer> map) {
		int line = 0;
		int totalCnt = 0;
		Set<Entry<String, Integer>> entrySet = map.entrySet();
		Iterator<Entry<String, Integer>> it = entrySet.iterator();
		while (it.hasNext()) {
			Entry<String, Integer> next = it.next();
			totalCnt += next.getValue();
			System.out.println("第" + (line + 1) + "行,个数" + next.getValue() + ":" + next.getKey());
			line++;
		}
		System.out.println("单词总数:" + totalCnt);
	}

	// 找出全文的单词，转小写，并排序
	public static void getAllWord() {
		try {
			String fileName = "/WordStatistics.txt";// 文件路径
			String resFileName = Constant.PATH_RESOURCES + fileName;
			BufferedReader br = new BufferedReader(new FileReader(resFileName));
			List<String> words = br.lines().flatMap(line -> Stream.of(line.split(" ")))
					.filter(word -> word.length() > 0).map(String::toLowerCase).distinct().sorted()
					.collect(Collectors.toList());
			br.close();
			System.out.println(words);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void getAllWord2() {
		String fileName = "/WordStatistics.txt";// 文件路径
		String resFileName = Constant.PATH_RESOURCES + fileName;
		String fileBody = ResourceUtil.readFile(resFileName);
		String[] wordArr = fileBody.split("[^a-zA-Z]"); // 过滤出只含有字母的
		List<String> wordList = new ArrayList<String>(); // 存储过滤后单词的列表
		for (String word : wordArr) {
			if (word.length() != 0) { // 去除长度为0的行
				wordList.add(word);
			}
		}
		Map<String, Integer> wordsCount = StatisticsFreq(wordList);
		SortMap(wordsCount);
		System.out.println(wordsCount.size());
	}

	// 统计词频
	public static Map<String, Integer> StatisticsFreq(List<String> wordList) {
		Map<String, Integer> wordsCount = new TreeMap<String, Integer>(); // 存储单词计数信息，key值为单词，value为单词数

		// 单词的词频统计
		for (String li : wordList) {
			if (wordsCount.get(li) != null) {
				wordsCount.put(li, wordsCount.get(li) + 1);
			} else {
				wordsCount.put(li, 1);
			}
		}
		return wordsCount;
	}

	// 按value的大小进行排序
	public static void SortMap(Map<String, Integer> oldmap) {

		ArrayList<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(oldmap.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue(); // 降序
			}
		});

		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
		}
	}

	// 找出全文的单词，转小写，并排序,使用 distinct 来找出不重复的单词。
	public static List<String> getDistinctWord() {
		try {
			// String fileName = "ANC.txt";// 文件路径
			// String fileName = "ANC_all.txt";// 文件路径
			String fileName = "/ANC_spoken.txt";// 文件路径
			String resFileName = Constant.PATH_RESOURCES + fileName;
			BufferedReader br = new BufferedReader(new FileReader(resFileName));
			// List<String> words = br.lines().flatMap(line -> Stream.of(line.split(" ")))
			// .filter(word -> word.length() >
			// 0).map(String::toLowerCase).distinct()//.sorted()
			// .collect(Collectors.toList());

			List<String> words = br.lines().distinct().collect(Collectors.toList());

			br.close();
			ResourceUtil.writerFile("distinct_ANC_spoken.txt", words, false);
			System.out.println(words);
			return words;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
