package com.myblog.statistics;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.map.HashedMap;

import com.myblog.Constant;
import com.myblog.txt.GenerateHtml;
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


public class ParseArticleWordsLevel {

	public static void main(String args[]) {
		String wordFile = Constant.PATH_RESOURCES + "/WordStatistics.txt";
		String fileBody = ResourceUtil.readFile(wordFile);
		String htmlBody = completeReplace(fileBody);
		
		String htmlFileName = Constant.PATH_RESOURCES +"/wordSts.html";
		String htmlTitle = "word Statistics";
		GenerateHtml.WriteHtmlWithBody(htmlFileName,htmlBody,htmlTitle);
	}
	
	/* 
	 *  
	 */  
	public static String completeReplace(String text){
		Map<String, String> bgColor= new HashedMap<String, String>();
		bgColor.put("1", "#4682B4");
		bgColor.put("2", "#FF0000");
		bgColor.put("3", "#191970");
		bgColor.put("4", "#191970");
		Map<String, String> fgColor= new HashedMap<String, String>();
		fgColor.put("1", "#191970");
		fgColor.put("2", "#FFFF00");
		fgColor.put("3", "#DC143C");
		fgColor.put("4", "#FF0000");
		
		String regWord = "[a-z\\-A-Z]+";
		Pattern pattern = Pattern.compile(regWord, Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(text);
		StringBuffer sb = new StringBuffer();
		int i= 0;
		while (m.find()) {
			String fontFmt = "<span style=\"background-color:%s;color:%s;\">%s</span>";
			String wordBody = String.format(fontFmt, bgColor.get("1"), fgColor.get("2"),m.group());
			m.appendReplacement(sb, wordBody);
			i++;
		}
		m.appendTail(sb); // 注视掉这句的结果是bbcbbcb
		System.out.println(sb.toString()); // 不注释掉上句的结果是bbcbbcb@163.com
		return sb.toString();  
	}  
}