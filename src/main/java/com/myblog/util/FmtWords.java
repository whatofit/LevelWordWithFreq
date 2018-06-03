package com.myblog.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myblog.Constant;

public class FmtWords {

    public FmtWords() {
    }

//    // 5386 whereverconj.无论在哪里          ad.无论在哪里,究竟在哪里
//    //整数+单词+词性1+词义
//    public static String fmtWord(String line) {
//        //System.out.println("fmtword,line=" + line);
//        //匹配数字+空白+
//        String regExNum = "([\\d]+)";
//        String regPos = "(art\\.|conj\\.|pron\\.|n\\.|v\\.|vi\\.|vt\\.|a\\.|ad\\.)";
//        
//        Pattern pNum = Pattern.compile(regExNum);
//        Matcher matcherNum = pNum.matcher(line);
//        if (matcherNum.find()) {
//            String num = matcherNum.group(1);
//            //System.out.println("fmtword,num=" + num);
//            String lineMean = line.replaceFirst(num, "").trim(); // 删除数字
//            //System.out.println("fmtword,remove num,line=" + line);
//            Pattern pPos = Pattern.compile(regPos);
//            Matcher matcherPos = pPos.matcher(lineMean);
//            if (matcherPos.find()) {
//                String pos = matcherPos.group(1);
//                //System.out.println("fmtword,pos=" + pos);
//                String regex = pos.replaceFirst("\\.", "\\\\."); //把pos中的英文句号转义，否则对于n.分隔如下line会出错：3    abdomen n.腹，下腹(胸部到腿部的部分)
//                //System.out.println("fmtword,pos2=" + regex);
//                String []words = lineMean.split(regex);
//                String word = words[0].trim();
//                String mean = words[1].trim();
//                mean = mean.replaceAll("\\s{2,}", " "); //移除多余的连续空格，只留下一个空格。
//                //System.out.println("fmtword,remove num,line0=" + word);
//                //System.out.println("fmtword,remove num,line1=" + mean);
//                //System.out.println("fmtword,remove num,line2=" + mean.replaceAll("\\s{2,}", " "));
//                return num + "\t" + word + "\t" + pos + mean;
//            }
//        }
//        return line;
//    }
    
//    // 5386 whereverconj.无论在哪里          ad.无论在哪里,究竟在哪里
//    //整数+单词+词性1+词义
//    public static String fmtWord(String line) {
//        //System.out.println("fmtword,line=" + line);
//        //匹配数字+空白+
//        String regExNum = "([\\d]+)";
//        String regExPoint = "[\\.]";//第一个英文句号
//        String regExPos = "(art\\.|conj\\.|pron\\.|n\\.|v\\.|vi\\.|vt\\.|a\\.|ad\\.|adv\\.|prep\\.|num\\.|aux\\.|int\\.)";
//        
//        Pattern pNum = Pattern.compile(regExNum);
//        Matcher matcherNum = pNum.matcher(line);
//        if (matcherNum.find()) {
//            String num = matcherNum.group(1);
//            //System.out.println("fmtword,num=" + num);
//            String lineMean = line.replaceFirst(num, "").trim(); // 删除数字
//            //System.out.println("fmtword,remove num,line=" + line);
//            String []segment = lineMean.split(regExPoint);//先把第一个引文句号后面的删除，只保留前面的字符串
//            Pattern pPos = Pattern.compile(regExPos);
//            String beginMean = segment[0]+".";
//            //System.out.println("fmtword,remove num,beginMean=" + beginMean);
//            Matcher matcherPos = pPos.matcher(beginMean);
//            if (matcherPos.find()) {
//                String pos = matcherPos.group(1);
//                //System.out.println("fmtword,pos=" + pos);
//                String regex = pos.replaceFirst("\\.", "\\\\."); //把pos中的英文句号转义，否则对于n.分隔如下line会出错：3    abdomen n.腹，下腹(胸部到腿部的部分)
//                //System.out.println("fmtword,pos2=" + regex);
//                String []words = lineMean.split(regex);
//                String word = words[0].trim();
//                String mean = words[1].trim();
//                mean = mean.replaceAll("\\s{2,}", " "); //移除多余的连续空格，只留下一个空格。
//                mean = mean.replaceAll("\t", " "); //移除多余的连续空格，只留下一个空格。
//                //System.out.println("fmtword,remove num,line0=" + word);
//                //System.out.println("fmtword,remove num,line1=" + mean);
//                //System.out.println("fmtword,remove num,line2=" + mean.replaceAll("\\s{2,}", " "));
//                return num + "\t" + word + "\t" + pos + mean;
//            }
//        }
//        return line;
//    }
    
	// stress n & v
	// strict a
	// strike(struck,struck /stricken) v & n
    // burn(burnt,burnt 或-ed,-ed) v & n
    // businessman/woman (pl businessmen/ women) n 
    // p.m./pm,P.M./PM abbr
    // seventeenth
	public static String fmtWord(String line) {
		// 匹配数字+空白+
		String regFirstWord = "^([a-zA-Z\\.-]+)";// "\\."英文句号
		Pattern pWrod = Pattern.compile(regFirstWord);
		Matcher matcherWord = pWrod.matcher(line);
		if (matcherWord.find()) {
			String word = matcherWord.group(1);
			// System.out.println("fmtword,word=" + word);
			String linePos = line.replaceFirst(word, "").trim(); // 删除数字
			// System.out.println("fmtword,remove linePos=" + linePos);
			return word + "\t" + linePos;
		}
		return line;
	}
    
    public static void main(String[] args) {
//        List<String> lines = ResourceUtil.readFileLines("stageFiles/word_Graduate_Entrance_Exam5500.txt");
//        for (int i = 0; i < lines.size(); i++) {
//            String line = lines.get(i).trim();
//            if (!line.startsWith("#") && !"".equals(line)) {
//                String fmt =fmtWord(line);
//                ResourceUtil.writerFile("stageFiles/word_Graduate_Entrance_Exam5500_new.txt", fmt,true);
//            }
//        }
    	
      List<String> lines = ResourceUtil.readFileLines(Constant.PATH_RESOURCES +"/2018高考大纲-英语-word.txt");
      for (int i = 0; i < lines.size(); i++) {
          String line = lines.get(i).trim();
          if (!line.startsWith("#") && !"".equals(line)) {
              String fmt =fmtWord(line);
              ResourceUtil.writerFile(Constant.PATH_RESOURCES +"/2018高考大纲-英语-word-fmt-result.txt", fmt,true);
          }
      }
      //String line = "strike(struck,struck /stricken) v & n";
    //	String line = "burn(burnt,burnt 或-ed,-ed) v & n";
//    	String line = "businessman/woman (pl businessmen/ women) n";
//    	String line = "p.m./pm,P.M./PM abbr";
//    	String line = "seventeenth";
//      String fmt =fmtWord(line);
//      ResourceUtil.writerFile(Constant.PATH_RESOURCES +"/2018高考大纲-英语-for-fmt-result.txt", fmt,true);
//      System.out.println("fmtword,fmt=" + fmt);
    	
        
//        String line = "11 about   ad.在周围；大约   prep.关于；在周围 a.准备";
//        String fmt =fmtWord(line);
//        ResourceUtil.writerFile("stageFiles/word_Graduate_Entrance_Exam5500_new.txt", fmt,true);
//        System.out.println("fmtword,fmt=" + fmt);
//        
//        String s[] = line.split("\\.");
//        System.out.println("fmtword,fmt0=" + s[0]);
//        System.out.println("fmtword,fmt1=" + s[1]);
    }

}
