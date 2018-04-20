package com.myblog.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseHtmlDemo {

    public ParseHtmlDemo() {
    }

    public static void main(String[] args) {
        String wordFile = "D:/make.html";
        String level = parseBaseLevel(FileUtil.readFile(wordFile));
        System.out.println("level:" + level);
        //getElement();
    }

//    public static void getElement() throws IOException {
//        File file = new File("D:/CSDN.NET.html");
//
//        String str标题 = "";
//        String strURL = "";
//        String str阅读次数_全 = "";
//        String str阅读次数 = "";
//        String str评论数_全 = "";
//        String str评论数 = "";
//        Document doc = Jsoup.parse(file, "UTF-8");
//        Document docSub;// 博客每一项
//        Elements elmPerLink;// 列表中的每一个博客超链接
//        // ---------------------------------------------
//        Elements content = doc.getElementsByClass("article_title");
//        for (int i = 0; i < content.size(); i++) {
//            docSub = Jsoup.parse(content.get(i).toString());
//            // 标题+链接
//            elmPerLink = content.get(i).getElementsByTag("a");
//            str标题 = elmPerLink.get(0).text();
//            strURL = elmPerLink.get(0).attr("href");
//
//            str阅读次数_全 = docSub.getElementsByClass("link_view").text();
//            str阅读次数 = getNum(str阅读次数_全);
//
//            str评论数_全 = docSub.getElementsByClass("link_comments").text();
//            str评论数 = getNum(str评论数_全);
//
//            System.out.println("标题: " + str标题);
//            System.out.println("URL: " + strURL);
//            System.out.println("阅读次数（原文字）: " + str阅读次数_全);
//            System.out.println("阅读次数: " + str阅读次数);
//            System.out.println("评论数: " + str评论数);
//        }
//    }
//
//    /**
//     * 提取数字
//     * 
//     * @param阅读(100) @return 100
//     */
//    public static String getNum(String str) {
//        int start = str.indexOf("(");
//        return str.substring(start + 1, str.length() - 1);
//    }
    
//    public static void demo2() throws IOException{
//        //jsoup文档：https://jsoup.org/apidocs/
//        String from = "CNY";
//        String to = "USD";
//        String url = "http://hl.anseo.cn/cal_"+from+"_To_"+to+".aspx";
//        Document doc = Jsoup.connect(url).get();
//        Element  result = doc.getElementById("result");
//        if(null != result ){
//            Element pElement = result.getElementsByTag("p").get(1);
//            System.out.println("所需的字符串："+pElement);
//            String [] array = pElement.html().split(" ");
//            int i = 0;
//            for(String aString : array){
//                System.out.println("处理之后，array["+i+"]=\""+aString+"\"");
//                i++;
//            }
//            String fromName = array[1];
//            String toName = array[4];
//            //double除法有问题，用bigdecimal
//            BigDecimal a1 = new BigDecimal(array[3]);    
//            BigDecimal rate = a1.multiply(new BigDecimal("0.01"));
//            System.out.println("结果所需要的五个元素："+fromName+"("+from+"),"+toName+"("+to+"),"+rate.doubleValue());
//            System.out.println("汇率结果："+rate.doubleValue());
//        }
//    }
    
    public static String parseBaseLevel(String htmlTxt) {
        String regExPos = "(CET4|CET6|考研|IELTS|TOEFL|GRE)";
        Document doc = Jsoup.parse(htmlTxt);
        Elements esBaseLevel = doc.getElementsByClass("base-level");
        for(Element e : esBaseLevel){
//            //System.out.println("level级别："+e.text());
//            Pattern pPos = Pattern.compile(regExPos);
//            Matcher matcherPos = pPos.matcher(e.text());
//            if (matcherPos.find()) {
//                //return e.text();
//            }
//            Elements pElement = e.getElementsByTag("p");
//            System.out.println("pElement："+pElement.text());
//            Elements sElement = e.getElementsByTag("span");
//            System.out.println("sElement："+sElement.text());
//            //Element sElement0 = sElement.get(0);
//            //System.out.println("sElement0:"+sElement0.text());
//            //if(StringUtils.isNotBlank(type)){}
//            String [] array = sElement.html().split("/");
//            System.out.println("levels："+Arrays.toString(array));
//            int i = 0;
//            for(String aString : array){
//                String tmp = aString.replaceAll("&nbsp;", "").trim();
//                System.out.println("处理之后，array["+i+"]=\""+tmp+"\"");
//                i++;
//            }
            
//            //System.out.println("level级别："+e.text());
//          Pattern pPos = Pattern.compile(regExPos);
//          Matcher matcherPos = pPos.matcher(e.text());
//          if (matcherPos.find()) {
//              //return e.text();
//          }
//          Elements sElement = e.getElementsByTag("span");
//          String [] array = sElement.html().split("/");
//          System.out.println("levels："+Arrays.toString(array));
//          int i = 0;
//          for(String aString : array){
//              String tmp = aString.replaceAll("&nbsp;", "").trim();
//              System.out.println("处理之后，array["+i+"]=\""+tmp+"\"");
//              i++;
//          }
            
            //System.out.println("level级别："+e.text());
          Pattern pPos = Pattern.compile(regExPos);
          Matcher matcherPos = pPos.matcher(e.text());
          if (matcherPos.find()) {
              //return e.text();
          }
          Elements sElement = e.getElementsByTag("span");
          String tmp = sElement.html().replaceAll("&nbsp;", "").replaceAll("\\s", "");
          
//        Elements sElement = e.getElementsByTag("span");
//        String tmp = sElement.text().replaceAll("&nbsp;", "").replaceAll("\\s", "");
          System.out.println("level级别："+tmp);
        }
        return "";
    }
    
}
