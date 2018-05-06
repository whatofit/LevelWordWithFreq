package com.myblog.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import org.apache.commons.collections4.map.HashedMap;

import com.myblog.Constant;
import com.myblog.demo.GenerateHtmlDemo;
import com.myblog.util.RegEx;
import com.myblog.util.ResourceUtil;

//1.load article加载文章
//2.找单词并把单词投到不同的stage中，包括单词的freq频率与分级level
//3.六排序模式：
//  a.先按照stage从小到大排序，相同stage，按照word在“大众文章中”出现的freq频率从高到底排序(等于按照单词在词频文件中的freq排序，同d.)
//  b.先按照stage从小到大排序，相同stage，按照word在“这个文章中”出现的count次数从高到底排序
//  c.先按照stage从小到大排序，相同stage，按照word的字母表顺序从底到高排序
//  d.打破stage限制，把所有单词加入到一个list中(或者把所有单词合并到一个stage中)，统一按照word在“大众文章中”出现的freq频率从高到底排序
//  e.打破stage限制，把所有单词加入到一个list中(或者把所有单词合并到一个stage中)，统一按照word在“这个文章中”出现的count次数从高到底排序
/// f.打破stage限制，把所有单词加入到一个list中(或者把所有单词合并到一个stage中)，统一按照word的字母表顺序从底到高排序
//4.输出

//todo:
//把文章中的所有单词按照单词level分层
//分层：一级上/下(小学),二级上/下(初中),三级上/下(高中),四级上/下(大学)，五级单词
//二中输出模式(不输出低层级单词)：1.先按照层级排序，再按照词频排序；2先按照词频输出，再按照层级输出
//1.把阶段stageFiles文件中单词读取到5个集合中出来
//2.把指定文件的单词统计出来
//3.把2中的单词映射到1中的stageFiles中，并输出。
public class ParseArticleWords_Level_Layer {

	private static List<String> vocabularyIgnore;

	ParseArticleWords_Level_Layer() // 初始化字典树
	{

	}

	public static void main(String args[]) {
		String vocabularyFile = Constant.PATH_RESOURCES + "/vocabulary_ignore.txt";
		vocabularyIgnore = ResourceUtil.readFileLines(vocabularyFile);
		
		String wordFile = Constant.PATH_RESOURCES + "/WordStatistics.txt";
		String fileBody = ResourceUtil.readFile(wordFile);
//		fileBody = fileBody.replaceAll("\n", "<br/>");
		String htmlBody = completeReplace(fileBody);

		String htmlFileName = Constant.PATH_RESOURCES + "/wordSts2.html";
		String htmlTitle = "word Statistics";
		GenerateHtmlDemo.WriteHtmlWithBody(htmlFileName, htmlBody, htmlTitle);
		System.out.println("done!");
	}

	/*
	 * 
	 * 文字前景色/背景色的颜色搭配
	 * http://www.360doc.com/content/14/0629/21/10756795_390813208.shtml
	 */
	public static String completeReplace(String text) {
		String vocabularyFile = Constant.PATH_RESOURCES + "/vocabulary_stage_levels_words.txt";
		List<String> vocabularyDict = ResourceUtil.readFileLines(vocabularyFile);

		Map<String, String> bgColor = new HashMap<String, String>();
		// White #FFFFFF
		// Black #000000
		bgColor.put("0", "#FFFFFF");// Black
		bgColor.put("1", "#0000FF");// Blue
		bgColor.put("2", "#00FF00");// Green
		bgColor.put("3", "#FFD700");// Gold
		bgColor.put("4", "#A020F0");// Purple
		bgColor.put("5", "#00FFFF");// Cyan
		bgColor.put("6", "#8B0000");// DarkRed
		bgColor.put("7", "#FF0000");// Red
		Map<String, String> fgColor = new HashMap<String, String>();
		fgColor.put("0", "#000000");
		fgColor.put("1", "#FFFFFF");
		fgColor.put("2", "#FFFFFF");
		fgColor.put("3", "#FFFFFF");
		fgColor.put("4", "#FFFFFF");
		fgColor.put("5", "#FFFFFF");
		fgColor.put("6", "#FFFFFF");
		fgColor.put("7", "#FFFFFF");

		String regWord = "[a-z\\-A-Z`]+";
		Pattern pattern = Pattern.compile(regWord, Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(text);
		StringBuffer sb = new StringBuffer();
		// int i = 0;
		while (m.find()) {
			String curWord = m.group();
			String level = getWordLevel(vocabularyDict, curWord);
			System.out.println("level=" + level);
			String fontFmt = "<span style=\"background-color:%s;color:%s;\">%s</span>";
			String wordBody = String.format(fontFmt, bgColor.get(level), fgColor.get(level), curWord);
			m.appendReplacement(sb, wordBody);
			// i++;
		}
		m.appendTail(sb); // 注视掉这句的结果是bbcbbcb
		System.out.println(sb.toString()); // 不注释掉上句的结果是bbcbbcb@163.com
		return sb.toString().replaceAll("\n", "<br/>");
	}

	/* 
	 *  
	 */
	public static String getWordLevel(List<String> vocabularyDict, String word) {
		for (String line : vocabularyIgnore) {
			if (word.equalsIgnoreCase(line)) {
				return "0";//基础单词，不涂色
			}
		}
		for (String line : vocabularyDict) {
			if (RegEx.isMatchWord(line, word)) {
				String[] wordField = line.split("\t");
				for (int j = 3; j < wordField.length; j++) {
					if ("1".equals(wordField[j])) {
						return (j - 3 + 1) + "";
					}
				}
			}
		}

		return "7";
	}
}