package com.myblog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myblog.model.Word;


public class RegEx {
    /**
    * @param regex
    * 正则表达式字符串
    * @param txt
    * 要匹配的字符串
    * @return 如果txt 符合 regex的正则表达式格式,返回true, 否则返回 false;
    */
	public static boolean isMatch(String regex, String txt) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(txt);
        return matcher.matches();
    }
	
    /**
     * 在字符串中找单词
    * @param regex
    * 要找的单词
    * @param txt
    * 待找的字符串、
    * @return 如果txt 有符合 regex单词,返回true, 否则返回 false;
    */
	public static boolean isMatchWord(String line, String word) {
		String[] wordField = line.split("\t");
		String newLine = wordField[0] + "/" + wordField[1];
		String []newField = newLine.split("/");
		for (int j = 0; j < newField.length; j++) {
			if (word.equalsIgnoreCase(newField[j])) {
				return true;
			}
		}
        return false;
    }
    
//    5294    vitamin n.维生素
//    5295    vivid   a.鲜艳的;生动的,栩栩如生的
//    5296    vocabulary  n.词汇,词汇量;词汇表
//    5297    vocal   a.声音的;有声的;歌唱的 n.元音;声乐作品
//    ★ accession
//    ★ accessory
//       accident
//       accidental
//    ▲ acclaim
//    ★ accommodate
//       accommodation
//       accompany
//    ▲ accomplice
//       accomplish
//       accord
    public static Word catchFreqLevelWord(String line) {
//        if (line.trim())
//        Word freqLevelWord = new Word();
        Word levelWord = new Word();
        // /匹配双字节字符(包括汉字在内)：[^x00-xff]
        String regEx = "([^x00-xff])\\s*([a-zA-Z()\\-\']+)";//匹配一个宽字符 空白(空格) 大小写字母或连字符号(Coca-Cola/ice-cream/living-room/t-shirt/up-to-date/x-ray) 单引号(o'clock),不匹配数字
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(line);
        if (matcher.find()) {
            // int gc = matcher.groupCount();
            // System.out.println("gc = " + gc);
            // 一般要求4794个单词（含中学已学词汇），表中不设标记；
            // 较高要求1601个单词，表中标记为★；
            // 更高要求1281个单词，表中标记为▲。
            String cet = matcher.group(1);
            String word = matcher.group(2);
//          中考18Level
//          高考35Level
//          四级46Level
//          考研55Level
//          六级64Level
//          String level = "cet4";
//          if (cet.equals("★")) {
//              level = "cet6";
//          } else if (cet.equals("▲")) {
//              level = "cet8";
//          } else {
//              level = "cet4";
//          }
            String level = "";
            if (cet.equals("★")) {
                level = "六级";
            } else if (cet.equals("▲")) {
                level = "更高要求";
//              word = "";
            } else if(isNumber(cet)){
                levelWord.setFrequency(cet);
            } else {
                level = "四级";
            }
            levelWord.setSpelling(word.replaceAll("[()]", "")); // 删除()小括号
//          levelWord.setSpelling(word.replaceAll("[()]", "").toLowerCase()); // 删除()小括号
            levelWord.setLevel(level);
        }
        return levelWord;
      }
    
    //hydrology
	public static Word toLevelWord(String line) {
		Word levelWord = new Word();
		// /匹配双字节字符(包括汉字在内)：[^x00-xff]
		//String regEx = "([\\d|^x00-xff]+)\\s*([a-zA-Z()\\-\']+)";//匹配一个宽字符 空白(空格) 大小写字母或连字符号(Coca-Cola/ice-cream/living-room/t-shirt/up-to-date/x-ray) 单引号(o'clock),不匹配数字
		String regEx = "([^x00-xff]|\\d+)\\s*([a-zA-Z()\\-\']+)";//匹配一个宽字符 空白(空格) 大小写字母或连字符号(Coca-Cola/ice-cream/living-room/t-shirt/up-to-date/x-ray) 单引号(o'clock),不匹配数字
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(line);
		if (matcher.find()) {
			// int gc = matcher.groupCount();
			// System.out.println("gc = " + gc);
			// 一般要求4794个单词（含中学已学词汇），表中不设标记；
			// 较高要求1601个单词，表中标记为★；
			// 更高要求1281个单词，表中标记为▲。
			String cet = matcher.group(1);
			String word = matcher.group(2);
//			中考18Level
//			高考35Level
//			四级46Level
//			考研55Level
//			六级64Level
//			String level = "cet4";
//			if (cet.equals("★")) {
//				level = "cet6";
//			} else if (cet.equals("▲")) {
//				level = "cet8";
//			} else {
//				level = "cet4";
//			}
			String level = "";
			if (cet.equals("★")) {
				level = "六级";
			} else if (cet.equals("▲")) {
				level = "更高要求";
//				word = "";
            } else if(isNumber(cet)){
                levelWord.setFrequency(cet);
			} else {
				//level = "四级";
			}
			levelWord.setSpelling(word.replaceAll("[()]", "")); // 删除()小括号
//			levelWord.setSpelling(word.replaceAll("[()]", "").toLowerCase()); // 删除()小括号
			levelWord.setLevel(level);
		}
		return levelWord;
	  }
	
	// ★ authoritative
	// ▲ austere
	// ★ authorize/-ise
	// ax(e)
	// ★ behavio(u)ral
	// airplane/aeroplane
	public static String[] catchWord(String line) {
		// String []ret ={"",""};
		String[] arr = new String[2];
		// /匹配双字节字符(包括汉字在内)：[^x00-xff]
		String regEx = "([^x00-xff])\\s*([a-zA-Z()]+)";
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(line);
		if (matcher.find()) {
			int gc = matcher.groupCount();
			// System.out.println("gc = " + gc);
			// 一般要求4794个单词（含中学已学词汇），表中不设标记；
			// 较高要求1601个单词，表中标记为★；
			// 更高要求1281个单词，表中标记为▲。
			String cet = matcher.group(1);
			String word = matcher.group(2);
			String level = "";
			String freq = "";
			if (cet.equals("★")) {
				level = "64";
			} else if (cet.equals("▲")) {
				level = "77";
			} else if(isNumber(cet)){
			    freq = cet;
			} else {
				level = "48";
			}
			word = word.replaceAll("[()]", ""); // 删除()小括号
			// System.out.println("group 1-2:" +cet + "," + word);

			// for(int i = 0; i <= gc; i++) {
			// System.out.println("group " + i + " :" + matcher.group(i));
			// }
			arr[0] = word;
			arr[1] = level;
		}
		return arr;
	}

	// 获取4级单词/6级单词/大学要求所有单词
	// level:4/6/8
	public static String catchWord(String line, int level) {
		// /匹配双字节字符(包括汉字在内)：[^x00-xff]
		String regEx = "([^x00-xff])\\s*([a-zA-Z()]+)";
		Pattern p = Pattern.compile(regEx);
		Matcher matcher = p.matcher(line);
		if (matcher.find()) {
			// int gc = matcher.groupCount();
			// System.out.println("gc = " + gc);
			// 一般要求4794个单词（含中学已学词汇），表中不设标记；
			// 较高要求1601个单词，表中标记为★；
			// 更高要求1281个单词，表中标记为▲。
			String cet = matcher.group(1);
			String word = matcher.group(2);
			if (level <= 4) {
				if (cet.equals("★")) {
				} else if (cet.equals("▲")) {
				} else {
					return word.replaceAll("[()]", ""); // 删除()小括号
				}
			} else if (level <= 6) {
				if (cet.equals("★")) {
					return word.replaceAll("[()]", ""); // 删除()小括号
				} else if (cet.equals("▲")) {
				} else {
					return word.replaceAll("[()]", ""); // 删除()小括号
				}
			} else {
				return word.replaceAll("[()]", ""); // 删除()小括
			}
		}
		return "";
	}

	// 从指定的(第一个匹配的)字符串，删除到行尾 ,String tag
	public static String removeTrail(String line,String tag) {
		return line.replaceAll(tag+".*$", "");
	}
	
    /**
    * 验证字符串是否为整数
    * 
    * @param 待验证的字符串
    * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
    */
    public static boolean isNumber(String line) {
        String regex = "^[0-9]*$";
        return isMatch(regex, line);
    }

    
	// 判断字符串中是否包含数字
	public static boolean containsNumber(String line) {
		String regex="[\\d]+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(line);
		return matcher.find();
	}
	
	// 取符串中的第一个数字
	// 3	and	c
	// 7/9	to	t/i
	// 12/27/903	that	c/d/r
	// 20/4665	this	d/r
	public static int catchFirstNumber(String line) {
		String regex = "^(\\d+).*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line.trim());
        if (matcher.find()) {
            String freq = matcher.group(1);
            return Integer.parseInt(freq );
        }
        return 0;
	}
	
	// 摄取本行第一个整数,第一个单词，第二个整数
    public static Word catchNumberWord(String line) {
        String []arr = line.trim().split("\\s");
        if (arr.length == 1) {
            return new Word(arr[0]);
        } else {
            Word word = toLevelWord(line);
            if (word.getSpelling() == null) {
                if (arr.length >= 2) {
                    word = new Word(arr[0],arr[1]);
                }
            }
            return word;
        }
    }
    
    // 14 you p
    // 29 n''t x
    // 29 n't x 
    // 48 will v
    // 6863 welcome n  
    //3652 and/or c 
    //8288 sauté v  
    //4103 o''clock r  
    //12253 y''all p  
    public static String catchWordLine(String line) {
        String regex = "^(\\d+)\\s+([a-zA-Z\\-\'/é]+)(?:\\s*\\[PL\\])?\\s+([a-zA-Z])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line.trim().replaceAll("''", "'"));
        if (matcher.find()) {
            String freq = matcher.group(1);
            String word = matcher.group(2);
            String pos = matcher.group(3);
            return freq +"\t"+word+"\t" + pos;
        }
        return "";
    }
    
	public static void main(String[] args) {
	    //String line = "★ behavio(u)ral";
//	    String line = "▲ acclaim";
//		 String line = "★ authorize/-ise";
//		 String line = "  ax(e) ";
//		String line = "  airplane/aeroplane(r)";
//      String line = "believe";
//	    String line = "654 finish";
	    //String line = "5297   vocal   a.声音的;有声的;歌唱的 n.元音;声乐作品";
//	    String line = "6863 welcome n  ";
//	    String line = "2846 | 0.96 ";
//	    String line = "9826 | 0.93 ";
	    //String line = "9975 semifinal [PL] n  ";
	    //String line = "9975 semifinal n ";
	    String line = "9997 supernova      ";
      
		//catchWord(line);
		
		//System.out.println("removeTrail=" + removeTrail(line,"/"));
		
		//System.out.println("replaceAll=" + line.replaceAll("[()\\d]", ""));
		
		//System.out.println("containsNumber=" + containsNumber(line));
//	    System.out.println("containsNumber:" + catchNumberWord(line));
	    
	    System.out.println("containsNumber:" + catchWordLine(line));
	}

//	week - n. a period of time equal to seven days
//	weigh - v. to measure how heavy someone or something is
//	welcome - v. to express happiness or pleasure when someone arrives or something develops
//	well - ad. in a way that is good or pleasing; in good health; n. a hole in the ground where water, gas or oil can be found
//	west - n. the direction in which the sun goes down
//	wet - ad. covered with water or other liquid; not dry
    public static String parseWordOfVOA(String line) {
        // 匹配一个单词开始，空白(空格) 连字符 所有字符
        String regEx = "(^\\w+)\\s*-?\\s*(.*)";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(line);
        if (matcher.find()) {
            String word = matcher.group(1);
            String mean = matcher.group(2);
            return word + "\t"+ mean ;
        }
        return "" + "\t"+ line;
        //return new String[] { "", line };
    }
    
    
//按照行合并单词，若某行以-连字符结尾，则把连字符去掉，把下一行拼接到其后；
//弱这行不是以-连字符结尾，则在行尾添加空格，再把下一行拼接到其后。
    //换句话说，若行尾是Hyphen，则直接删除行尾Hyphen，或行尾不是连字符，则拼接一个空格到行尾。
    public static String removeEndHyphen(String line) {
    	//String regex = "\\.*-$";
    	if (line.endsWith("-")) {
    		return line.substring(0,line.length()-1);
    		//return line.replaceAll(regex, replacement)
    	} else {
    		return line + " ";
    	}
	}
}
