package com.myblog.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.myblog.Constant;
import com.myblog.util.CfgUtil;
import com.myblog.util.RegEx;
import com.myblog.util.ResourceUtil;
import com.myblog.util.Utils;

public class ParseEnglishText {

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
        // 1.读取单词集合路径
        String cfg_englist_path = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_englist_txt");
		//String forderPath = "E:/FanMingyou/The Economist";
		String englistFile = Constant.PATH_RESOURCES +cfg_englist_path;
        // 2.加载集合
        List<String> wordLines = doParseEnglishFile(englistFile);
//        // #剔重/删除重复单词,保留唯一单词并排序
//        List<String> uniqueWords = wordLines.parallelStream()
//                .filter(line -> !line.equals("-"))
//                .map(line -> line.toLowerCase().trim())
//                .filter(line -> !line.equals(""))
//                .distinct()
//                .sorted()
//                .collect(Collectors.toList());
        Map<String, Integer> wordsCount = WordStatistics.StatisticsFreq(wordLines);
        //System.out.println(wordsCount.size());
        List<String > wordFreqList = WordStatistics.SortMap(wordsCount);
        System.out.println(wordFreqList.size());
		
        // 2.写入对应的text文件
        String cfg_englist_txt_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_englist_txt_result");
        String outTextFilename = Constant.PATH_RESOURCES +cfg_englist_txt_result;
        ResourceUtil.writerFile(outTextFilename, wordFreqList, false);
        System.out.println("done!outTextFilename="+outTextFilename);
        
        long endTime = System.currentTimeMillis();
        System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
	}

	// 
	public static List<String> doParseEnglishFile(String englistFile) {
		//1.读目录中的txt文件
		//2.去掉每行尾带的连字符后直接拼接，另把每行不带连字符的行，加空格拼接成文本
		//3.分词成list并加入总词list
		List<String> txtFileList = Utils.traverseFile(englistFile, ".txt");
		
		List<String> wordLines  = new ArrayList<String>();
		for (String txtFilename:txtFileList) {
            String fileText = doRemoveHyphenAnd2Text(txtFilename);
            //wordLines.add(fileText);
            //WordStatistics.countWords(fileText);
    		String regWord = "[a-z\\-A-Z]+";
    		Pattern pattern = Pattern.compile(regWord, Pattern.CASE_INSENSITIVE);
    		Matcher m = pattern.matcher(fileText);
    		while (m.find()) {
    			String curWord = m.group();
    			if (curWord.startsWith("-")) {
    				//12-country trade deal
    				//50-feet //16-point //150-seat
    				curWord = curWord.replaceFirst("-", ""); 
    			}
    			if (!"".equals(curWord.trim())){
    				wordLines.add(curWord.toLowerCase());
    			}	
    		}
    	}
		return wordLines;
	}
	
	 public static String doRemoveHyphenAnd2Text(String filename) {
			//''Constant.PATH_RESOURCES + 
			 List<String> fileLines = ResourceUtil.readFileLines(filename);
		     List<String> newLines = fileLines.parallelStream()
		                //.filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
		                // .filter(line -> !line.contains("-"))
		                .map(RegEx::removeEndHyphen)
		                //.filter(line -> !line.trim())
		                //.distinct()
		                //.sorted()
		                .collect(Collectors.toList());
		     
		     StringBuffer sbTxt = new StringBuffer("");
		     for (String newLine:newLines) {
		    	 sbTxt.append(newLine);
		     }
			return sbTxt.toString();
		}
	    
}
