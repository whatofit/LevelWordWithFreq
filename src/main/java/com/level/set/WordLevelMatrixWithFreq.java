package com.level.set;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.level.Constant;
import com.level.util.ResourceUtil;


public class WordLevelMatrixWithFreq {

	private WordLevelMatrixWithFreq(){}
    private static final Logger LOGGER = LoggerFactory.getLogger(WordLevelMatrixWithFreq.class);
	
    //1.小学/2.初中/3.高中/4.大学/
    //5.新概念英语
    //6.四级/7六级/8考研/9专四/10.专八
    //11.大学英语课程教学要求(4/6/8)
    //BEC(BUSINESS ENGLISH CERTIFICATE/商务英语考试)/
    //CATTI(China Accreditation Test for Translators and Interpreters/翻译专业资格（水平）考试)
    //IELTS(雅思)
    //TOEFL(托福)
    //SAT(美国学术能力评估测试/美国高考)
    //GRE(美国研究生入学考试)
    //GMAT(商学院入学考试/研究生管理科学入学考试)
    //LSAT(法学院入学考试)
    //MBA(Master of Business Administration/工商管理硕士)
    //TSE(英语口语考试)
    //TOEIC(托业/Test of English for International Communication)
    
	static String [][]fileLevel = {
			{"小学","/word_primary_school.txt",},//1353
			{"初中","/word_junior_school.txt"},//2171
			{"高中","/word_senior_school.txt",},//2361
			{"大学","/word_university.txt",},//1864
			{"大学教纲","/大学英语课程教学要求.txt"},//
			{"新概念","/word_new_conception.txt"},
			{"四级","/word_CET4.txt"},
			{"六级","/word_CET6.txt"},
			{"考研","/word_考 研.txt"},
			{"专四","/word_TEM4.txt"},
			{"专八","/word_TEM8.txt"},
			{"BEC","/word_BEC.txt"},
			{"CATTI","/word_CATTI.txt"},
			{"IELTS","/word_IELTS.txt"},
			{"TOEFL","/word_TOEFL.txt"},
			{"SAT","/word_SAT.txt"},
			{"GRE","/word_GRE.txt"},
			{"GMAT","/word_GMAT.txt"},
			{"MBA","/word_MBA.txt"},
			{"TOEIC","/word_TOEIC.txt"},
		};
    
    /**
     * 获取单词列表,
     * 
     * 文件格式：每行只有单词，没有单词序号或行序号，也没有词频
     * 
     * 文件格式:每行2列，第1列：词频/序号/行号；第2列：单词；
     * 
     * 中间用空格/空白隔开
     * 
     * 规则：如果每行文本仅有1列，则为单词列，若超过1列(即大于等于2列)，则第二列为单词列(第一列为序号、行号或词频)
     * 
     * 把以#号开始的行当做注释，忽略本行单词。
     * 
     * @return Map<String, Map<String, String>>
     */
    public static Map<String, Map<String, String>> getWordList(String[]... files){
//        Set<Word> setResult = new HashSet<>();
//        Map<String, String> map = new HashMap<String, String>();
        // word,courseLevel,0/1/2/3
        Map<String, Map<String, String>> mapResult = Collections.synchronizedMap(new HashMap<String, Map<String, String>>());
        for(String []file : files){
            System.out.println("parse word file: " +file[0] +","+ file[1]);
            List<String> words = ResourceUtil.readFileLines(Constant.PATH_STAGE + file[1]);
            for (String line : words) {// 遍历set去出里面的的Key
            	//用"-2"替代"★",用"-3"替代"▲",把左右小括号()删除掉，把斜线/之后的字符串删除(到行尾)
            	line = line.trim().replaceAll("★", "-2").replaceAll("▲", "-3").replaceAll("/.*$", "");
            	if (!line.startsWith("#") && !"".equals(line)) {
            		String []segments = line.split("\\s+");//以空白(空格/tab键/回车/换行)分割
            		String word="";
            		String courseValue = "1";
            		if (segments.length <= 1)
            		{
            			word = segments[0];
            		}else{
            			word = segments[1];
            			if (segments[0].equals("-2")){
            				courseValue = "2";
            			} else if (segments[0].equals("-3")){
            				courseValue = "3";
            			} else {
            				courseValue = "1";
            			}
            		}
            		word = word.replaceAll("[()\\d]", "");//去掉序号
            		Map<String, String> mapWord = mapResult.get(word);
            		if (mapWord == null){
            			mapWord = new HashMap<String, String>();
            			mapWord.put(file[0], courseValue); //
            			mapResult.put(word, mapWord);
            		}else
            		{
            			mapWord.put(file[0], courseValue); //
            		}
            	}
    		}
             
            System.out.println("wordSet count: "+words.size());
        }
        System.out.println("unique words count: "+mapResult.size());
        return mapResult;
    }

    /**
     * 根据《American National Corpus,ANC.txt》词汇列表保存文件
     * 
     * 文件格式:每行2列，第1列：词频；第2列：单词；
     * 
     * 中间用空格/空白隔开、
     * 
     * @return void 
     * 
     */
	public static void outputWithMatrix(Map<String, Map<String, String>> mapResult, String path){
    	String file = "/American National Corpus,ANC.txt";
    	List<String> wordList = new ArrayList<String> ();
    	try {
    	    List<String> words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + file);
			LOGGER.info("开始保存词典：" + path);
			StringBuilder sbTitle= new StringBuilder();
			sbTitle.append("freq\tword\t");
			for (int j = 0;j <fileLevel.length;j++) {
				sbTitle.append(fileLevel[j][0]+"\t");
        	}
			wordList.add(sbTitle.toString());
			
			for (int i = 0; i < words.size(); i++) {
            	//用"-2"替代"★",用"-3"替代"▲",把左右小括号()删除掉，把斜线/之后的字符串删除(到行尾)
				String line = words.get(i).trim();
            	if (!line.startsWith("#") && !"".equals(line)) {
            		String []segments = line.split("\\s+");//以空白(空格/tab键/回车/换行)分割
            		String word = "";
            		String freq ="99999";//默认较大的频率
            		if (segments.length <= 1)
            		{
            			word = segments[0];
            		}
            		else{
            			word = segments[1];
            			freq = segments[0];
            		}
            		StringBuilder sb= new StringBuilder();
        			sb.append(freq+"\t"+word+"\t");
            		Map<String, String> mapWord = mapResult.get(word);
            		if (mapWord != null){
            	    	for (int j = 0;j <fileLevel.length;j++) {
            	    		String courseValue = mapWord.get(fileLevel[j][0]);
            	    		courseValue=courseValue==null?"":courseValue;
            	    		sb.append(new String(courseValue)+"\t");
	                	}
            			mapResult.remove(word);
            		}
            		wordList.add(sb.toString());
            	}
			}
			System.out.println("leave words count: "+mapResult.size());
			for (Iterator it = mapResult.entrySet().iterator(); it.hasNext();) {
				Map.Entry entry2 = (Map.Entry) it.next();
				String word = (String) entry2.getKey();
				Map<String, String> mapWord = (Map<String, String>) entry2.getValue();
				StringBuilder sb= new StringBuilder();
    			sb.append("99999"+"\t"+word+"\t");
    	    	for (int j = 0;j <fileLevel.length;j++) {
    	    		String courseValue = mapWord.get(fileLevel[j][0]);
    	    		courseValue=courseValue==null?"":courseValue;
    	    		sb.append(courseValue+"\t");
            	}
    			wordList.add(sb.toString());
			}
			Files.write(Paths.get(path), wordList);
			LOGGER.info("保存成功");
   	  }catch (Exception e){
   	      LOGGER.error("保存词典失败", e);
   	  }
      //System.out.println("unique words count: "+set.size());
    }

	
	public static void main(String[] args) {
		String toSaveFile = "";
		Map<String, Map<String, String>> mapResult = getWordList(fileLevel);//先获取level比较低的单词集合，后获取levle较高的集合
    	System.out.println("all unique words count: "+mapResult.size());
    	
    	toSaveFile = Constant.PATH_RESOURCES + "/wordLevelsFile_new.txt";
    	outputWithMatrix(mapResult,toSaveFile);
	}

}
