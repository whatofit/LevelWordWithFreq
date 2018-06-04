package com.myblog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myblog.model.Word;

//正则表达式官方文档：
//https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
//Summary of regular-expression constructs

/*
以下正则表达式待验证。。。
\ 将下一个字符标记为一个特殊字符、或一个原义字符、或一个后向引用、或一个八进制转义符。
常用的正则表达式：
匹配空行的正则表达式：\n[\s| ]*\r
匹配中文字符的正则表达式： [\u4e00-\u9fa5]
中文：^[\u0391-\uFFE5]+$
匹配双字节字符(包括汉字在内)：[^\x00-\xff]
匹配首尾空格的正则表达式：(^\s*)|(\s*$)
匹配IP地址的正则表达式：/(\d+)\.(\d+)\.(\d+)\.(\d+)/g //
匹配HTML标记的正则表达式：/<(.*)>.*</1>|<(.*) />/
匹配HTML标记：<(.*)>.*<\/\1>|<(.*) \/>
匹配Email地址的正则表达式：w+([-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*
E-mail地址： ^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$
匹配网址URL的正则表达式：http://([w-]+.)+[w-]+(/[w- ./?%&=]*)?
URL：^[a-zA-Z]+://(\w+(-\w+)*)(\.(\w+(-\w+)*))*(\?\s*)?$ 或：^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$
sql语句：^(select|drop|delete|create|update|insert).*$
*/

//介绍如何使用java.util.regex包
//正则表达式在"字符串模式-匹配"和"字符串模式-替换"方面富有弹性。
//在regex包中，包括了两个类，Pattern(模式类)和Matcher(匹配器类)。
//Pattern类是用来表达和陈述所要搜索模式的对象，
//Matcher类是真正影响搜索的对象。
//另加一个新的例外类，PatternSyntaxException，当遇到不合法的搜索模式时，会抛出例外。

//对java的解释器来说，在反斜线字符(/)前的字符有特殊的含义。在java中，与regex有关的包，并不都能理解和识别反斜线字符(/)
//即为了让反斜线字符(/)在模式对象中被完全地传递，应该用双反斜线字符(/),此外圆括号在正则表达中两层含义，如果想让它解释为字面上意思(即圆括号)，也需要在它前面用双反斜线字符(/)

/*
字符
x 字符 x。举例：'a'表示字符a
\\ 反斜线字符。
\n 新行（换行）符 ('\u000A') 
\r 回车符 ('\u000D')

字符类
[abc] a、b 或 c（简单类） 
[^abc] 任何字符，除了 a、b 或 c（否定） 
[a-zA-Z] a到 z 或 A到 Z，两头的字母包括在内（范围） 
[0-9] 0到9的字符都包括

预定义字符类
. 任何字符。若是.字符本身，用\.表示
\d 数字：[0-9]
\D 非数字:[^\d]/[^0-9]
\w 单词字符：[a-zA-Z_0-9]
\W 非字符[^\w]

边界匹配器
^ 行的开头 
$ 行的结尾 
\b 单词边界， 就是不是单词字符的地方。

Greedy 数量词 
X? X，一次或一次也没有
X* X，零次或多次
X+ X，一次或多次
X{n} X，恰好 n 次 
X{n,} X，至少 n 次 
X{n,m} X，至少 n 次，但是不超过 m 次 

运算符 
　　XY		X后跟 Y 
　　X|Y 		X 或 Y 
　　(X)		X，作为捕获组
*/

//String类中的三个基本操作使用正则：
//匹配:matches()
//切割:split()
//替换:replaceAll()

/*
java中正则匹配的对象：
  pattern:
	Pattern		Pattern.complie(regexString)
	Macther		Pattern.matches(regexString)
  Matcher：
	boolean		matcher.find() //查找下一个匹配对象
	boolean		matcher.matches() //尝试将整个区域与模式匹配
	int			matcher.groupCount() //返回匹配规则的分组，如：(aa)(bb)：这表示两组
	String		matcher.guorp() //返回整个匹配模式匹配到的结果
	String		matcher.group(int group)	//返回匹配对象对应分组的匹配结果
	MatcheResult  matcher.toMatchResult()	//将匹配结果一MatchResult的形式返回
*/

/*
^ 匹配输入字符串的开始位置。如果设置了 RegExp 对象的Multiline 属性，^ 也匹配 ’\n’ 或 ’\r’ 之后的位置。
$ 匹配输入字符串的结束位置。如果设置了 RegExp 对象的Multiline 属性，$ 也匹配 ’\n’ 或 ’\r’ 之前的位置。
* 匹配前面的子表达式零次或多次。
+ 匹配前面的子表达式一次或多次。+ 等价于 {1,}。
? 匹配前面的子表达式零次或一次。? 等价于 {0,1}。
{n} n 是一个非负整数，匹配确定的n 次。
{n,} n 是一个非负整数，至少匹配n 次。
{n,m} m 和 n 均为非负整数，其中n <= m。最少匹配 n 次且最多匹配 m 次。在逗号和两个数之间不能有空格。

? 当该字符紧跟在任何一个其他限制符 (*, +, ?, {n}, {n,}, {n,m}) 后面时，匹配模式是非贪婪的。非贪婪模式尽可能少的匹配所搜索的字符串，而默认的贪婪模式则尽可能多的匹配所搜索的字符串。
. 匹配除 "\n" 之外的任何单个字符。要匹配包括 ’\n’ 在内的任何字符，请使用象 ’[.\n]’ 的模式。

(pattern) 匹配pattern 并获取这一匹配。
(?:pattern) 匹配pattern 但不获取匹配结果，也就是说这是一个非获取匹配，不进行存储供以后使用。
(?=pattern) 正向预查，在任何匹配 pattern 的字符串开始处匹配查找字符串。这是一个非获取匹配，也就是说，该匹配不需要获取供以后使用。
(?!pattern) 负向预查，与(?=pattern)作用相反

x|y 匹配 x 或 y。
[xyz] 字符集合。
[^xyz] 负值字符集合。
[a-z] 字符范围，匹配指定范围内的任意字符。
[^a-z] 负值字符范围，匹配任何不在指定范围内的任意字符。
\b 匹配一个单词边界，也就是指单词和空格间的位置。
\B 匹配非单词边界。
\cx 匹配由x指明的控制字符。
\n 匹配一个换行符。等价于 \x0a 和 \cJ。
\r 匹配一个回车符。等价于 \x0d 和 \cM。
\t 匹配一个制表符。等价于 \x09 和 \cI。
\v 匹配一个垂直制表符。等价于 \x0b 和 \cK。
\f 匹配一个换页符。等价于 \x0c 和 \cL。
\s 匹配任何空白字符，包括空格、制表符、换页符等等。等价于[ \f\n\r\t\v]。
\S 匹配任何非空白字符。等价于 [^ \f\n\r\t\v]。

\d 匹配一个数字字符。等价于 [0-9]。
\D 匹配一个非数字字符。等价于 [^0-9]。
\w 匹配包括下划线的任何单词字符。等价于’[A-Za-z0-9_]’。
\W 匹配任何非单词字符。等价于 ’[^A-Za-z0-9_]’。

\xn 匹配 n，其中 n 为十六进制转义值。十六进制转义值必须为确定的两个数字长。
\num 匹配 num，其中num是一个正整数。对所获取的匹配的引用。

\n 标识一个八进制转义值或一个后向引用。如果 \n 之前至少 n 个获取的子表达式，则 n 为后向引用。否则，如果 n 为八进制数字 (0-7)，则 n 为一个八进制转义值。
\nm 标识一个八进制转义值或一个后向引用。如果 \nm 之前至少有is preceded by at least nm 个获取得子表达式，则 nm 为后向引用。如果 \nm 之前至少有 n 个获取，则 n 为一个后跟文字 m 的后向引用。如果前面的条件都不满足，若 n 和 m 均为八进制数字 (0-7)，则 \nm 将匹配八进制转义值 nm。
\nml 如果 n 为八进制数字 (0-3)，且 m 和 l 均为八进制数字 (0-7)，则匹配八进制转义值 nml。
\\un 匹配 n，其中 n 是一个用四个十六进制数字表示的Unicode字符。

*/

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
    
	// 拆分一行文本为一个单词,及自定义的其他，
	//本行内容放到sentences, 单词放到spelling字段，【除单词之外的其他(可拼接)meanings字段,】
	//比如集合1的每行如下：
	//2	abandon	核	vt.离弃，丢弃；遗弃，抛弃；放弃
	//5	able	基	a.有(能力、时间、知识等)做某事，有本事的
	//5432	zone	核	n.地区,区域 v.分区,划分地带
	//
	//结合2的每行如下:
	//able
	//zoo
	public static int spelling_Idx = 0;
    public static Word split2Word(String line) {
        String []field = line.trim().split("\\s");
        String spelling = "";
        if (spelling_Idx < field.length) {
        	spelling = field[spelling_Idx];
        } 
        Word word = new Word(removeBrackets(spelling)); //spelling.replaceAll("[()]", ""); 删除()小括号
        word.setSentences(line);
        return word;
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
	    
//	    System.out.println("containsNumber:" + catchWordLine(line));
//	    String content = "i want to [thank] you (state) [thank] very(dfljsj)nishishui";
//	    String retLine = removeBrackets(content);
//	    System.out.println("removeBrackets:" + retLine);
	    
	  	//String content = "src: local('Open Sans Light'), local('OpenSans-Light'), url(http://fonts.gstatic.com/s/opensans/v13/DXI1ORHCpsQm3Vp6mXoaTa-j2U0lmluP9RWlSytm3ho.woff2) format('woff2')";
	    String content = "shrink(shrank,shrunk 或 shrunk,shrunken) v";
	  	String retLine = catchWordInBrackets(content);
	  	System.out.println("removeBrackets:" + retLine);
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
    
    //String str="[\u4e00-\u9fa5]";    //该表达式可以识别出任何汉字。
    //String punctuation =  "[\\pP+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]"; //标点（所有中英文标点）的正则表达式
    
    //匹配中文标点符号：
    //String str="[\u3002\uff1b\uff0c\uff1a\u201c\u201d\uff08\uff09\u3001\uff1f\u300a\u300b]"
    //该表达式可以识别出： 。 ；  ， ： “ ”（ ） 、 ？ 《 》 这些标点符号。
     
//    //java中匹配字符串中的中文字符(含中文标点的)
//    public static String catchChinese(String src) {
//        String exp="^[\u4E00-\u9FA5|\\！|\\,|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]$";  
//        Pattern pattern=Pattern.compile(exp);  
//        for (int i = 0; i < src.length(); i++) {// 遍历字符串每一个字符  
//            char c = str.charAt(i);  
//            Matcher matcher=pattern.matcher(c + "");  
//            if(matcher.matches()) {  
//                amount++;  
//            }  
//        }  
//        return amount;  
//        
//        String regex = "^(\\d+)\\s+([a-zA-Z\\-\'/é]+)(?:\\s*\\[PL\\])?\\s+([a-zA-Z])$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(line.trim().replaceAll("''", "'"));
//        if (matcher.find()) {
//            String freq = matcher.group(1);
//            String word = matcher.group(2);
//            String pos = matcher.group(3);
//            return freq +"\t"+word+"\t" + pos;
//        }
//        return "";
//    }  

//    //java中匹配并获取前导字符串中的英文字符(含英文标点)，及数字，直到碰到汉字为准
//    public static String catchEnglish(String src) {
//        //([^\u4e00-\u9fa5])
//        String regex = "^(\\d+)\\s+([a-zA-Z\\-\'/é]+)(?:\\s*\\[PL\\])?\\s+([a-zA-Z])$";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(line.trim().replaceAll("''", "'"));
//        if (matcher.find()) {
//            String freq = matcher.group(1);
//            String word = matcher.group(2);
//            String pos = matcher.group(3);
//            return freq +"\t"+word+"\t" + pos;
//        }
//        return "";
//    }  
    
    //Java正则表达式:去掉括号()内任意字符
    //1.字符集合:	非)右括号的所有字符[^)]
    //2.次数匹配:	0或多次[^)]+
    //3.转义: 	()是正则的关键字，所以要反斜杠转义 /( 反斜杠也是关键字，也要转义 //(
    	    //[^字符]是一个匹配模式，所以里面的 ) 不用转义
    //正确的表达式结果:    正则表达式 ([^)]+) 转义后是\\([^)]\\)
    public static String removeBrackets(String line) {
		//String pattern = "\\[[^\\]]+\\]";//中括号内  
		String pattern = "\\([^)]*\\)";//括号内  
		//String pattern = "\\(.+";  
		line = line.replaceAll(pattern, "");  
		return line;
	}
    
    //java正则表达式匹配小括号内的内容
    //a(an) art
    //above prep,a & ad
    //a.m./am,A.M./AM abbr
    //be(am,is,are,was,were,being,been) v
    //do(did,done) v
    //dormitory(dorm) n
    //dream(dreamt,dreamt 或-ed,-ed) n & v
    //shrink(shrank,shrunk 或 shrunk,shrunken) v
    //forget(forgot,forgot/forgotten) v
    //cheque(Am check) n
    //fall2(Am)=autumn n
    //favourite(Am favorite) a & n
    //dialogue(Am dialog) n
    public static String catchWordInBrackets(String line) {
    	// 从内容上截取路径数组
    	//Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");  
    	//Pattern pattern = Pattern.compile("([a-z])(?<=\\()[^\\)]*");//(\\w)\\s*(?<=\\()[^\\)]*\\s*(.+)
    	Pattern pattern = Pattern.compile("(?<=\\()[^\\)]+");
		Matcher matcher = pattern.matcher(line);
		if (matcher.find()) {
			String bracketContent = matcher.group();
			return bracketContent;
			// line = line.replaceFirst("("+bracketContent+")", "");
		}
//    	 while(matcher.find()){
//    	    System.out.println(matcher.group());
//    	 }
		return "";
	}
}
