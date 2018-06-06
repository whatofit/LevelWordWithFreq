package com.myblog.rtf2txt.fmt;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.myblog.Constant;
import com.myblog.util.CfgUtil;
import com.myblog.util.ResourceUtil;

public class ParseAmericanFamilyParentChildEnglish {

    public ParseAmericanFamilyParentChildEnglish() {
        
    }
    
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        // 1.读取单词集合路径
        //String forderPath = "E:/FanMingyou/The Economist";
        String englistFile = Constant.PATH_RESOURCES + "/美国家庭万用亲子英文英语8000句.txt";
        // 2.加载file
        String fileBody = ResourceUtil.readFile(englistFile);
//        List<String> fileLines = ResourceUtil.readFileLines(englistFile);
//        String fileBody = mergeLinesWithBlank(fileLines);
        fileBody = fileBody.replaceAll("\r\n", " ");//不加中括号，只替换一个空格
        //合并行结尾与行开头同类字符的行，若是英文则加空格
        // 2.处理coca原文件集合为word list
        List<String> chineseLines = pickChinese(fileBody);
        List<String> ecLinse = pickEnglishAndChinese(fileBody,chineseLines);
        System.out.println(ecLinse.size());
        // 2.写入对应的text文件
        String cfg_english_txt_result_path = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_english_txt_result");
        String outTextFilename = Constant.PATH_RESOURCES +cfg_english_txt_result_path;
        ResourceUtil.writerFile(outTextFilename, ecLinse, false);
//        ResourceUtil.writerFile(outTextFilename, fileBody, false);
        System.out.println("done!outTextFilename="+outTextFilename);
        
        long endTime = System.currentTimeMillis();
        System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
    }
//
//    // 
//    public static List<String> fmtFileLinesWithMerge(List<String> fileLines) {
//        List<String> wordLines = new ArrayList<String>();
//        for (String curFileLine : fileLines) {// 待循环(可能重复)列表
//            if (!curFileLine.contains("24小时内删除") && !curFileLine.contains("微信公众号") ) {
//                wordLines.add(curFileLine);
//            }
//        }
//        return wordLines;
//    }
    
//    //private static final String punctuation =  "[\\pP+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]"; //标点（所有中英文标点）的正则表达式
//    private static final String regExChinese = "[\u4e00-\u9fa5]";//该表达式可以识别出任何汉字。
//    private static final String regExEnglish = "[\\w\\s]";//\w 匹配任何字类字符，包括下划线。与"[A-Za-z0-9_]"等效。//\s 匹配任何空白字符，包括空格、制表符、换页符等。与 [ \f\n\r\t\v] 等效。
//    private static final String INPUT = "cat 匹配 cat 配任何字类字符 cattie 下划线 cat";
// 
//    public static void main( String args[] ){
//       Pattern pChinese = Pattern.compile(regExChinese);
//       Pattern pExEnglish = Pattern.compile(regExEnglish);
//       Matcher m = p.matcher(INPUT); // 获取 matcher 对象
//       int count = 0;
// 
//       while(m.find()) {
//         count++;
//         System.out.println("Match number "+count);
//         System.out.println("start(): "+m.start());
//         System.out.println("end(): "+m.end());
//      }
//       
//       INPUT.replaceAll( "" , "");        
//       INPUT.replaceAll( "[\\p{P}+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]" , "");   
//   }
    
    //private static final String REGEX = "[\\w\\s\\pP]+";
    private static final String REGEX = "[\u4e00-\u9fa5\\pP\\s]+";//\\d \\s
//    private static final String INPUT = "CHAPTER46-05祝贺信-新年"
//+"Have a very merry Christmas! 圣诞节快乐！"
//+" "
//+"Wishing you peace, love, and joy this Christmas!"
//+"希望你这次的圣诞节充满和平、爱和喜"
//+"乐！"
//+"Wishing you and your family a wonderful"
//+"Christmas!"
//+"希望您跟家人有个很棒的圣诞节！"
//+"May the Christmas season bring only happiness"
//+"and joy to you and your loved ones."
//+"希望这个圣诞节带给您跟您所爱的人幸"
//+"福和喜乐。"
//+"May the joy and happiness of Christmas last"
//+"throughout the new year!"
//+"希望圣诞节和欢乐和愉快一直延续到新"
//+"的一年！";
 
    
//    private static final String spt = "祝贺信-新年\n"
//+"圣诞节快乐！\n"
//+"希望你这次的圣诞节充满和平、爱和喜乐！\n"
//+"希望您跟家人有个很棒的圣诞节！\n"
//+"希望这个圣诞节带给您跟您所爱的人幸福和喜乐。\n"
//+"希望圣诞节和欢乐和愉快一直延续到新的一年！\n";
    //
//    public static void main(String args[]) {
//
//    }

    public static List<String> pickChinese(String src) {
        List<String> chineseLines = new ArrayList<String>();
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(src); // 获取 matcher 对象
        while (m.find()) {
            // System.out.println("Match number "+count);
            // System.out.println("start(): "+m.start());
            // System.out.println("end(): "+m.end());
            String dest = src.substring(m.start(), m.end());
            //System.out.println(dest);
            dest = dest.trim().replaceFirst("^\\pP", "").trim(); // line开始的标点符号,.replaceAll("[\\s]", "")并把合并行时的空格移除
            if (!dest.isEmpty() && !dest.equals("/")) {
                //System.out.println(dest);
                chineseLines.add(dest);
            }
        }
        return chineseLines;
    }

    public static List<String> pickEnglishAndChinese(String src, List<String> chineseLines) {
        List<String> englishAndChineseLines = new ArrayList<String>();
        int engStartInx = 0;
        for (String chnLine : chineseLines) {
            int idx = src.indexOf(chnLine,engStartInx);
            //System.out.println(engStartInx+"+"+idx);
            if (idx > engStartInx) {
                String engLine = src.substring(engStartInx, idx).trim();
                //System.out.println(engLine);//+"\t"+ chnLine
                englishAndChineseLines.add(engLine +"\t"+ chnLine.replaceAll("[\\s]", ""));
            }
            engStartInx = idx + chnLine.length();
        }
        return englishAndChineseLines;
    }
    
    public static String mergeLinesWithBlank(List<String> fileLines) {
        return String.join(" ", fileLines);
    }
    

}

