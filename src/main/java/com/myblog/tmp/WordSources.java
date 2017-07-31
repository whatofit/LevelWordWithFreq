/**
 * 
 * APDPlat - Application Product Development Platform
 * Copyright (c) 2013, 杨尚川, yang-shangchuan@qq.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package com.myblog.tmp;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myblog.model.Word;
import com.myblog.util.RegEx;

/**
 * 从多个文本文件中读取单词
 * 一行一个单词，单词和其他信息之间用空白字符隔开
 * @author 杨尚川
 */
public class WordSources {
    private WordSources(){}
    private static final Logger LOGGER = LoggerFactory.getLogger(WordSources.class);

    static String [][]fileLevel = {
		{"小学","/word_primary_school.txt",},//1353
		{"初中","/word_junior_school.txt"},//2171
		{"高中","/word_senior_school.txt",},//2361
		{"大学","/word_university.txt",},//1864
		{"大学教纲","/大学英语课程教学要求.txt"},//
//		{"新概念","/word_new_conception.txt"},
		{"四级","/word_CET4.txt"},
		{"六级","/word_CET6.txt"},
		{"考研","/word_考 研.txt"},
//		{"专四","/word_TEM4.txt"},
//		{"专八","/word_TEM8.txt"},
//		{"BEC","/word_BEC.txt"},
		
//		{"CATTI","/word_CATTI.txt"},
//		{"IELTS","/word_IELTS.txt"},
//		{"TOEFL","/word_TOEFL.txt"},
//		{"SAT","/word_SAT.txt"},
//		{"GRE","/word_GRE.txt"},
//		{"GMAT","/word_GMAT.txt"},
//		{"MBA","/word_MBA.txt"},
//		{"TOEIC","/word_TOEIC.txt"},
	};
    
    /**
     * 获取单词列表,
     * 
     * 文件格式：每行只有单词，没有单词序号或行序号，也没有词频
     * 
     * @return Set<Word>
     */
    public static Set<Word> getWordList(String... files){
        Set<Word> set = new HashSet<>();
        for(String file : files){
            URL url = null;
            if(file.startsWith("/")){
                url = WordSources.class.getResource("/resources"+file);
            }else{
                try {
                    url = Paths.get(file).toUri().toURL();
                }catch (Exception e){
                    LOGGER.error("构造URL出错", e);
                }
            }
            if(url == null){
                LOGGER.error("解析词典失败："+file);
                continue;
            }
            System.out.println("parse word file: "+url);
            List<String> words = getExistWords(url);
            Set<Word> wordSet = words.parallelStream()
                    .filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
                    .map(line -> new Word(line.trim().toLowerCase()))
//                    .map(line -> new Word(line.trim(),"64"))
                    //.filter(word -> StringUtils.isAlphanumeric(word.getWord()))
                    .collect(Collectors.toSet());
            set.addAll(wordSet);
            //System.out.println("wordSet count: "+wordSet.size());
        }
        System.out.println("unique words count: "+set.size());
        return set;
    }

    /**
     * 获取ANC频率单词列表
     * 
     * 文件格式:每行2列，第1列：词频/序号/行号；第2列：单词；
     * 
     * 中间用空格/空白隔开
     * 
     * @return Set<Word>
     */
    public static Set<Word> getWordFreqList(String... files){
        Set<Word> set = new HashSet<>();
        for(String file : files){
            URL url = null;
            if(file.startsWith("/")){
                url = WordSources.class.getResource("/resources"+file);
            }else{
                try {
                    url = Paths.get(file).toUri().toURL();
                }catch (Exception e){
                    LOGGER.error("构造URL出错", e);
                }
            }
            if(url == null){
                LOGGER.error("解析词典失败："+file);
                continue;
            }
            System.out.println("parse word file: "+url);
            List<String> words = getExistWords(url);
            Set<Word> wordSet = words.parallelStream()
            		.filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
                    .filter(line -> line.trim().split("\\s+").length >= 2)
                    .map(line -> new Word(Integer.parseInt(line.trim().toLowerCase().split("\\s+")[0]),line.trim().toLowerCase().split("\\s+")[1]))
//                    .map(line -> new Word(line.trim().toLowerCase().split("\\s+")[1]))
                    //.filter(word -> StringUtils.isAlphanumeric(word.getWord()))
                    .collect(Collectors.toSet());
            System.out.println("wordSet count: "+wordSet.size());
            set.addAll(wordSet);
        }
        System.out.println("unique words count: "+set.size());
        return set;
    }

    /**
     * 获取《大学英语课程教学要求.txt》词汇
     * College English Curriculum Requirements
     * 
     * 文件格式:每行2列，第1列：★或▲或无；第2列：单词；
     * 
     * 中间用空格/空白隔开，无单词序号也无行序号
     * 
     * @return Set<Word>
     */
    public static Set<Word> getCollegeRequirements2Level(){
    	String[] files ={"/大学英语课程教学要求.txt"};
    	
        Set<Word> set = new HashSet<>();
        for(String file : files){
            URL url = null;
            if(file.startsWith("/")){
                url = WordSources.class.getResource("/resources"+file);
            }else{
                try {
                    url = Paths.get(file).toUri().toURL();
                }catch (Exception e){
                    LOGGER.error("构造URL出错", e);
                }
            }
            if(url == null){
                LOGGER.error("解析词典失败："+file);
                continue;
            }
            System.out.println("parse word file: "+url);
            List<String> words = getExistWords(url);
            
            Set<Word> wordSet = words.parallelStream()
            		.filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
                    .map(RegEx::toLevelWord)
                    .collect(Collectors.toSet());
            set.addAll(wordSet);
            System.out.println("wordSet count: "+wordSet.size());
        }
        System.out.println("unique words count: "+set.size());
        return set;
    }
    
    /**
     * 获取《大学英语课程教学要求.txt》词汇
     * College English Curriculum Requirements
     * 
     * 文件格式:每行2列，第1列：★或▲或无；第2列：单词；
     * 
     * 中间用空格/空白隔开，无单词序号也无行序号
     * 
     * @return Set<Word>
     */
    public static Set<Word> getCollegeRequirements(int level){
    	String[] files ={"/大学英语课程教学要求.txt"};
        Set<Word> set = new HashSet<>();
        for(String file : files){
            URL url = null;
            if(file.startsWith("/")){
                url = WordSources.class.getResource("/resources"+file);
            }else{
                try {
                    url = Paths.get(file).toUri().toURL();
                }catch (Exception e){
                    LOGGER.error("构造URL出错", e);
                }
            }
            if(url == null){
                LOGGER.error("解析词典失败："+file);
                continue;
            }
            System.out.println("parse word file: "+url);
            List<String> words = getExistWords(url);

            //RegEx.catchWord(line,level)
//          Set<Word> wordSet = words.parallelStream()
//          		.filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
//                  .map(line -> new Word(RegEx.catchWord(line,level)))
//                  .filter(word -> !"".equals(word.getWord().trim()))
//                  .filter(word -> StringUtils.isAlphanumeric(word.getWord()))
//                  .collect(Collectors.toSet());
            
            //4级以下        
            Set<Word> wordSet1 = words.parallelStream()
            		.filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
            		.filter(line -> !line.contains("★") && !line.contains("▲"))
            		.map(line -> new Word(line.replaceAll("[()\\d]", "").replaceAll("/.*$", "").toLowerCase().trim()))
                    .collect(Collectors.toSet());
            
            //仅6级
            Set<Word> wordSet2 = words.parallelStream()
            		.filter(line -> line.contains("★"))
                    .map(line -> new Word(line.replaceAll("★", "").replaceAll("[()\\d]", "").replaceAll("/.*$", "").toLowerCase().trim()))
                    .collect(Collectors.toSet());
            
            //仅更高要求
            Set<Word> wordSet3 = words.parallelStream()
            		.filter(line -> line.contains("▲"))
                    .map(line -> new Word(line.replaceAll("▲", "").replaceAll("[()\\d]", "").replaceAll("/.*$", "").toLowerCase().trim()))
                    .collect(Collectors.toSet());
            
            //6级以下：较高要求
            Set<Word> wordSet4 = words.parallelStream()
            		.filter(line -> !line.trim().contains("▲"))
                    .map(line -> new Word(line.replaceAll("★", "").replaceAll("[()\\d]", "").replaceAll("/.*$", "").toLowerCase().trim()))
                    .collect(Collectors.toSet());
            
            //更高要求-全部-去重复-全转换为小写
            Set<Word> wordSet5 = words.parallelStream()
                    .map(line -> new Word(line.replaceAll("★", "").replaceAll("▲", "").replaceAll("[()\\d]", "").replaceAll("/.*$", "").toLowerCase().trim()))
                    .collect(Collectors.toSet());
            
          //仅重复词汇
          Set<Word> wordSet6 = words.parallelStream()
	    		.filter(line -> RegEx.containsNumber(line))
	    		.map(line -> new Word(line.replaceAll("★", "").replaceAll("▲", "").replaceAll("[()\\d]", "").toLowerCase().trim()))
	            .collect(Collectors.toSet());
            
          System.out.println("words count1: "+wordSet1.size());
          System.out.println("words count2: "+wordSet2.size());
          System.out.println("words count3: "+wordSet3.size());
          System.out.println("words count4: "+wordSet4.size());
          System.out.println("words count5: "+wordSet5.size());
          System.out.println("words count6: "+wordSet6.size());
          
            set.addAll(wordSet3);
        }
        System.out.println("unique words count: "+set.size());
        
        return set;
    }
    
    /**
     * 获取《大学英语课程教学要求.txt》词汇
     * College English Curriculum Requirements
     * 
     * 文件格式:每行2列，第1列：★或▲或无；第2列：单词；
     * 
     * 中间用空格/空白隔开，无单词序号也无行序号
     * 
     * @return List<String> 
     * 
     */
    //public static List<String> getCollegeRequirements(){
	public static Set<Word> getCollegeRequirements(){
    	String[] files ={"/大学英语课程教学要求.txt"};
//    	List<String> wordList = new ArrayList<String> ();
    	Set<Word> set = new HashSet<>();
        for(String file : files){
            URL url = null;
            if(file.startsWith("/")){
                url = WordSources.class.getResource("/resources"+file);
            }else{
                try {
                    url = Paths.get(file).toUri().toURL();
                }catch (Exception e){
                    LOGGER.error("构造URL出错", e);
                }
            }
            if(url == null){
                LOGGER.error("解析词典失败："+file);
                continue;
            }
            System.out.println("parse word file: "+url);
            List<String> words = getExistWords(url);
//            for (int i = 0; i < words.size();i++){
//            	String line = words.get(i);
//            	wordList.add(line.replaceAll("★", "").replaceAll("▲", "").replaceAll("[()\\d]", "").replaceAll("/.*$", "").trim());
//            }
            
            //更高要求-全部-去重复-全转换为小写
            Set<Word> wordSet5 = words.parallelStream()
            		.filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
                    .map(line -> new Word(line.replaceAll("★", "").replaceAll("▲", "").replaceAll("[()\\d]", "").replaceAll("/.*$", "").toLowerCase().trim()))
                    .collect(Collectors.toSet());
            System.out.println("words count5: "+wordSet5.size());
            set.addAll(wordSet5);
        }
//        System.out.println("unique words count: "+wordList.size());
//        System.out.println(wordList);
//        Collections.sort(wordList, new Comparator<String>() {
//            public int compare(String arg0, String arg1) {
//                return arg0.compareToIgnoreCase(arg1);
//            }
//        });
//        return wordList;
      System.out.println("unique words count: "+set.size());
      return set;
    }

    /**
     * 一行一个单词，单词和其他信息之间用空白字符隔开
     * 默认 index 为1
     * @param files 单词文件类路径，以/开头
     * @return 不重复的单词集合
     */
    public static Set<Word> get(String level, String... files){
        return get(1, level,files);
    }

    /**
     * 一行一个单词，单词和其他信息之间用空白字符隔开
     * @param index 单词用空白字符隔开后的索引，从0开始
     * @param files 单词文件类路径，以/开头
     * @return 不重复的单词集合
     */
    public static Set<Word> get(int index, String level, String... files){
        Set<Word> set = new HashSet<>();
        for(String file : files){
            URL url = null;
            if(file.startsWith("/")){
                url = WordSources.class.getResource("/resources"+file);
            }else{
                try {
                    url = Paths.get(file).toUri().toURL();
                }catch (Exception e){
                    LOGGER.error("构造URL出错", e);
                }
            }
            if(url == null){
                LOGGER.error("解析词典失败："+file);
                continue;
            }
            System.out.println("parse word file: "+url);
            List<String> words = getExistWords(url);
            
            Set<Word> wordSet = words.parallelStream()
                    .filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
                    .filter(line -> line.trim().split("\\s+").length >= index+1)
//                    .map(line -> new Word(line.trim().toLowerCase().split("\\s+")[index],level))
                    .map(line -> new Word(line.trim().split("\\s+")[index],level))
                    .filter(word -> StringUtils.isAlphanumeric(word.getSpelling())) //如果testString全由字母或数字组成，返回True
                    .filter(word -> !"".equals(word.getSpelling().trim()))
                    .collect(Collectors.toSet());
            set.addAll(wordSet);
            System.out.println("wordSet count: "+wordSet.size());
        }
        System.out.println("unique words count: "+set.size());
        return set;
    }
    
    private static List<String> getExistWords(URL url){
        try {
            return Files.readAllLines(Paths.get(url.toURI()));
        }catch (Exception e){
            return Collections.emptyList();
        }
    }
    
    public static Set<Word> stem(Set<Word> words){
        return words
                .stream()
                .filter(word -> word.getSpelling().length() > 3)
                .filter(word -> !isPlural(words, word))
                .collect(Collectors.toSet());
    }
    
    public static boolean isPlural(Set<Word> words, Word word){
        return isPlural(words, word, new HashMap<>());
    }
    
    public static boolean isPlural(Set<Word> words, Word word, Map<String, String> data){
        String w = word.getSpelling();
        //1、以辅音字母+y结尾,变y为i再加es
        if (w.endsWith("ies")){
            char c = w.charAt(w.length()-4);
            if(!(isVowel(c))
                    && words.contains(new Word(w.substring(0, w.length()-4)+"y"))){
                log(w, "ies");
                data.put(w, "ies");
                return true;
            }
        }
        //2、以ce, se, ze结尾, 加s
        if(w.endsWith("ces")
                || w.endsWith("ses")
                || w.endsWith("zes")){
            if(words.contains(new Word(w.substring(0, w.length()-1)))){
                log(w, "s");
                data.put(w, "s");
                return true;
            }
        }
        //3、以s, sh, ch, x结尾, 加es
        if(w.endsWith("ses")
                || w.endsWith("shes")
                || w.endsWith("ches")
                || w.endsWith("xes")){
            if(words.contains(new Word(w.substring(0, w.length()-2)))){
                log(w, "es");
                data.put(w, "es");
                return true;
            }
        }
        //4、一般情况，加s
        if(w.endsWith("s")){
            if(words.contains(new Word(w.substring(0, w.length()-1)))){
                log(w, "s");
                data.put(w, "s");
                return true;
            }
        }
        return false;
    }
    
    private static void log(String word, String suffix){
        LOGGER.debug("发现复数："+word+"\t"+suffix);
    }
    
    public static boolean isVowel(char _char){
        switch (_char){
            case 'a':return true;
            case 'e':return true;
            case 'i':return true;
            case 'o':return true;
            case 'u':return true;
        }
        return false;
    }
    
    public static void main(String[] args) {
    	long startTime =System.currentTimeMillis();
//        AtomicInteger i = new AtomicInteger();
//        stem(getSyllabusVocabulary()).forEach(w -> System.out.println(i.incrementAndGet() + "、" + w.getWord()));
        
//        String html = HtmlFormatter.toHtmlForPluralFormat(plural(getSyllabusVocabulary()));
//        System.out.println(html);

    	
    	String file1 = "";
    	String file2 = "";
    	String toLevel = ""; //要读取的文件，是某分级
    	String toSaveFile = "";
//    	file1 = "/CollegeRequirements4783一般要求-全小写.txt";  	//一般要求
//    	file1 = "/CollegeRequirements1579仅6级-全小写.txt";		//仅-较高要求
//    	file1 = "/CollegeRequirements1268-仅更高要求-全小写.txt";//仅-更高要求
//    	file1 = "/CollegeRequirements6362-全较高要求-全小写.txt";// 全-较高要求
//    	file1 = "/CollegeRequirements7629全-全小写.txt";		//全-全小写.txt
    	
//    	file1 = "/word_primary_school.txt"; //小学
//    	file1 = "/word_junior_school.txt";	//初中
//    	file1 = "/word_senior_school.txt";	//高中
//    	file1 = "/word_university.txt";		//大学
//    	toLevel ="primary_school";
//    	toLevel ="junior_school";
//    	toLevel ="word_senior_school";
//    	toLevel ="word_university";
    	
//    	String []files1 ={"/word_ADULT.txt", "/word_BEC.txt", "/word_CATTI.txt", "/word_CET4.txt", "/word_CET6.txt", "/word_GMAT.txt", "/word_GRE.txt", "/word_IELTS.txt", "/word_junior_school.txt", "/word_MBA.txt", "/word_new_conception.txt", "/word_primary_school.txt", "/word_SAT.txt", "/word_senior_school.txt", "/word_TEM4.txt", "/word_TEM8.txt", "/word_TOEFL.txt", "/word_TOEIC.txt", "/word_university.txt", "/word_考 研.txt"};
//    	String []files2 ={"/word_primary_school.txt","/word_junior_school.txt","/word_senior_school.txt","/word_university.txt"};
//    	String []files3 ={"/word_IELTS.txt","/word_TOEFL.txt","/word_GRE.txt","/word_SAT.txt","/word_GMAT.txt"};
    	
    	toSaveFile = "/toSaveFile_removeAll.txt";
//    	toSaveFile = "/minus3.txt";
//    	List<String> lst1 = new ArrayList<String>();
    	Set<Word> set1 = new HashSet<>();
    	Set<Word> set2 = new HashSet<>();
    	Set<Word> set3 = new HashSet<>();
    	Set<Word> setResult = new HashSet<>();
//    	Set<Word> words = new HashSet<>();
    	//words.addAll(get2());
//    	words.addAll(set1);
    	
//    	set1 = getCollegeRequirements2Level();
//    	set1 = getCollegeRequirements(4);//获取《大学英语课程教学要求.txt》词汇
//    	set1 = getCollegeRequirements();
    	
//    	file1 = "/大写单词.txt";
//    	file2 = "/小写单词.txt";
//    	set1 = getWordList(file1); 
//    	set2 = getWordFreqList(files2); 
    	
//    	set2 = getWordFreqList("/American National Corpus,ANC.txt");

//    	set1 = get("","/word_CET4.txt"); //CET4词汇
//    	set2 = get("","/word_CET6.txt"); //CET6词汇
//    	set3 = get("","/word_考 研.txt"); //考 研
//    	set2 = get("/word_university.txt");
//    	set1 = get("/word_IELTS.txt");
//    	set1 = get(toLevel, files1);
//    	set1 = get("/American National Corpus,ANC.txt");
//    	set2.retainAll(set1);//求交集
//    	set3.removeAll(set1); //先把当前集合(set1)中和结果集(setResult)重复的的单词全部除去；
//		setResult.addAll(set2);//再把修剪过的当前集合(set1)加入到结果集(setResult)中
		
//    	setResult = intersection(set1,set2);
//    	setResult = minus(set1,set2);
//    	setResult = minus(set1,minus(set2,set1));
//    	setResult = mergeWordLevelByFreq(set1,set2);
//    	save(set3,toSaveFile);
    	
//    	saveList(null,"/freqList.txt");

    	toSaveCourseLevelWord();
    	
		long endTime =System.currentTimeMillis();
		System.out.println("执行耗时 : "+(endTime-startTime)/1000f+" 秒 ");
    }
    
//  public static Map<Word, AtomicInteger> convert(Map<String, AtomicInteger> words){
//  Map<Word, AtomicInteger> result = new HashMap<>();
//  words.keySet().forEach(w -> result.put(new Word(w, ""), words.get(w)));
//  return result;
//}

//public static boolean isEnglish(String string){
//  for(char c : string.toLowerCase().toCharArray()){
//      if(c<'a' || c>'z'){
//          return false;
//      }
//  }
//  return true;
//}

/**
* 求交集
* @param first
* @param second
* @return
*/
public static Set<Word> intersection(Set<Word> first, Set<Word> second){
  LOGGER.info("求交集词典1："+first.size());
  LOGGER.info("求交集词典2："+second.size());
  Set<Word> result = first
          .stream()
          .filter(w -> second.contains(w))
          .collect(Collectors.toSet());
  
//  Set<Word> result = new HashSet<Word>();
//  result.clear();
//  result.addAll(first);
//  result.retainAll(second);
  
  LOGGER.info("交集词典："+result.size());
  return result;
}

//
public static Set<Word> mergeWordLevelByFreq(Set<Word> setAnc, Set<Word> setLevel){
	  LOGGER.info("setAnc个数："+setAnc.size());
	  LOGGER.info("setLevel个数："+setLevel.size());
	  
		Iterator<Word> itLevel = setLevel.iterator(); //Level
		while (itLevel.hasNext()) {
			Word wordLevel = (Word) itLevel.next();
			//System.out.println(wordLevel.getWord());
			boolean isFound = false; //是否在setAnc中找到当前单词
			Iterator<Word> itAnc = setAnc.iterator();//Freqency
			while (itAnc.hasNext()) {
				Word wordFreqency = (Word) itAnc.next();
				//System.out.println("\t"+wordFreqency.getWord());
				//if (wordFreqency.getWord().equalsIgnoreCase(wordLevel.getWord())) {
				if (wordFreqency.getSpelling().equals(wordLevel.getSpelling())) {
					//System.out.println("\t\twordFreqency==wordLevel");
					wordFreqency.setLevel(wordLevel.getLevel());
					isFound = true;
					break;
				}
			}
			if (!isFound) {
				wordLevel.setFrequency(70000); //使新加进语料库的单词排最后
				setAnc.add(wordLevel);//把新单词加入单词语料库
			}
		}
		LOGGER.info("结果个数："+setAnc.size());
		return setAnc;
		
	  
//	  Set<Word> result = first
//		.stream()
//		.filter(word -> second.contains(word))
//		.collect(Collectors.toSet());
			
//	  Set<Word> result = setAnc
//				.stream()
//				.filter(word -> word.getFrequency() == 1000)
//				.forEach(word -> word.setFrequency(1));
	  
//	  List<Shape> shapes = ...
//	  shapes.stream()
//      .filter(s -> s.getColor() == BLUE)
//      .forEach(s -> s.setColor(RED));
	  
//		Set<Word> result = setAnc.stream().sorted((a, b) -> b.getFrequency() - a.getFrequency()).collect(Collectors.toSet());
	  
//	  Collections.sort(names, (String a, String b) -> {
//		    return b.compareTo(a);
//		});
	  
//Collections.sort(names, (a, b) -> b.compareTo(a));
	  
//		//先转换成list，再排序
//	  List<Word> wordList = new ArrayList<Word>(result);
//      Collections.sort(wordList, new Comparator<Word>() {
//          public int compare(Word arg0, Word arg1) {
//          	int freq0 = arg0.getFrequency();
//          	int freq1 = arg1.getFrequency();
//          	  if (freq0 < freq1)
//                    return 1;
//                else if (freq0 > freq1)
//                    return -1;
//                else 
//                    //return 0;
//                	arg0.getWord().compareTo(arg1.getWord());
//          }
//      });
      
//		// Listing 3
//		Collections.sort(people, new Comparator<Person>() {
//			public int compare(Person lhs, Person rhs) {
//				if (lhs.getLastName().equals(rhs.getLastName())) {
//					return lhs.getAge() - rhs.getAge();
//				} else
//					return lhs.getLastName().compareTo(rhs.getLastName());
//			}
//		});
	        
//	  Iterator it = minuend.iterator();//先迭代出来  
//      while(it.hasNext()){//遍历  
//          System.out.println(it.next());  
//      }  
//	  LOGGER.info("结果个数："+wordList.size());
//	  return wordList;
//		LOGGER.info("结果个数："+result.size());
//		return result;
	}

public static Set<Word> minus(Set<Word> minuend, Set<Word> subtrahend){
  LOGGER.info("被减数个数："+minuend.size());
  LOGGER.info("减数个数："+subtrahend.size());
//  Set<Word> result = minuend
//          .stream()
//          .filter(word -> !subtrahend.contains(word))
//          .collect(Collectors.toSet());
  
		Set<Word> result = minuend
		.stream()
		.filter(word -> subtrahend.contains(word))
		.collect(Collectors.toSet());
		
//		//先转换成list，再排序
//		List<Word> wordList = new ArrayList<Word>(result);
//        Collections.sort(wordList, new Comparator<Word>() {
//            public int compare(Word arg0, Word arg1) {
//            	int freq0 = arg0.getFrequency();
//            	int freq1 = arg1.getFrequency();
//            	  if (freq0 < freq1)
//                      return 1;
//                  else if (freq0 > freq1)
//                      return -1;
//                  else
//                      return 0;
//            }
//        });
        
//  Set<Word> result = new HashSet<Word>();
//  result.clear();
//  result.addAll(minuend);
//  result.removeAll(subtrahend);
  LOGGER.info("结果个数："+result.size());
  return result;
}

public static void save(Set<Word> words, String path){
  try {
      path = "src/target" + path;  //父目录必须存在
//      File file = new File(path);
//      if (!file.exists()) {
//          file.mkdirs();
//      }
      LOGGER.info("开始保存词典：" + path);
      
      AtomicInteger ai = new AtomicInteger();
      List<String> list = words
                  .stream()
                  //.sorted()
                  .sorted(Word.m_byFreqAndLevel)
//                  .sorted((a, b) -> a.getFrequency() - b.getFrequency())
                  .map(word -> (word.getFrequency()+"\t"+word.getSpelling()+"\t"+word.getLevel()))
//                  .map(word -> (ai.incrementAndGet()+"\t"+word.getWord()+"\t"+word.getLevel()))
//                  .map(word -> word.getWord())
                  .collect(Collectors.toList());
      Files.write(Paths.get(path), list);
      LOGGER.info("保存成功");
  }catch (Exception e){
      LOGGER.error("保存词典失败:", e);
  }
}
 
//public static void saveList(List<Word> worList, String path){
//	  try {
//	      path = "src/target" + path;  //父目录必须存在
//	      LOGGER.info("开始保存词典：" + path);
//	      //Files.write(Paths.get(path), list);
//	      List<String> list = worList.stream().sorted()
//	    		  .map(word -> (word.getFrequency()+"\t"+word.getWord()))
//                  .collect(Collectors.toList());
//	      Files.write(Paths.get(path), list);
//	      LOGGER.info("保存成功");
//	  }catch (Exception e){
//	      LOGGER.error("保存词典失败", e);
//	  }
//	}

public static void save(List<String> list, String path){
  try {
      path = "src/target" + path;  //父目录必须存在
      LOGGER.info("开始保存词典：" + path);
      Files.write(Paths.get(path), list);
      LOGGER.info("保存成功");
  }catch (Exception e){
      LOGGER.error("保存词典失败", e);
  }
}

//  public static Map<String, String> plural(Set<Word> words){
//  Map<String, String> data = new HashMap<>();
//  words
//          .stream()
//          .filter(word -> word.getWord().length() > 3)
//          .forEach(word -> {
//              isPlural(words, word, data);
//          });
//  return data;
//}

public static void toSaveCourseLevelWord() {
    String [][]fileLevel = {
		{"小学","/word_primary_school.txt",},//1353
		{"初中","/word_junior_school.txt"},//2171
		{"高中","/word_senior_school.txt",},//2361
		{"大学","/word_university.txt",},//1864
		{"四级","/word_CET4.txt"},
		{"考研","/word_考 研.txt"},
		{"六级","/word_CET6.txt"},
//		{"大学教纲","/大学英语课程教学要求.txt"},//
	};
    Set<Word> set1 = new HashSet<>();
	Set<Word> setLevel = new HashSet<>();
	Set<Word> setResult = new HashSet<>();
	for (int i = 0;i <fileLevel.length;i++) {
		set1 = get(fileLevel[i][0],fileLevel[i][1]);//先获取level比较低的单词集合，后获取levle较高的集合
		set1.removeAll(setLevel); //先把当前集合(set1)中和结果集(setResult)重复的的单词全部除去；
		setLevel.addAll(set1);//再把修剪过的当前集合(set1)加入到结果集(setResult)中
	}
	set1 = getCollegeRequirements2Level();
	set1.removeAll(setLevel); //先把当前集合(set1)中和结果集(setLevel)重复的的单词全部除去；
	setLevel.addAll(set1);//再把修剪过的当前集合(set1)加入到结果集(setLevel)中
	
	set1 = getWordFreqList("/American National Corpus,ANC.txt");
	setResult = mergeWordLevelByFreq(set1,setLevel);
	
	String toSaveFile = "/toSaveCourseLevle2.txt";
	save(setResult,toSaveFile);
  }

}
