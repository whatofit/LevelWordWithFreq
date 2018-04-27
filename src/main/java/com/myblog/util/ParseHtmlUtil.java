package com.myblog.util;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.myblog.Constant;
import com.myblog.model.PercetWord;

public class ParseHtmlUtil {

    public ParseHtmlUtil() {
    }

    //解析单词在http://www.iciba.com中标识的词汇考试级别
    public static String parseIcibaBaseLevel(String htmlTxt) {
        String regExPos = "(CET4|CET6|考研|IELTS|TOEFL|GRE)";
        Document doc = Jsoup.parse(htmlTxt);
        Elements esBaseLevel = doc.getElementsByClass("base-level");
        for (Element e : esBaseLevel) {
            // Elements pElement = e.getElementsByTag("p");
            // System.out.println("pElement：" + pElement.text());
            // System.out.println("level级别："+e.text());
            Pattern pPos = Pattern.compile(regExPos);
            Matcher matcherPos = pPos.matcher(e.text());
            if (matcherPos.find()) {
                Elements sElement = e.getElementsByTag("span");
                return sElement.html().replaceAll("&nbsp;", "").replaceAll("\\s", "");
            }
        }
        return "";
    }
    
    //解析Word Content:包括:单词原型/词性/变形/词义及词义百分比,暂不考虑例句
    public static String[] parseDictWordContent(String htmlTxt) {
        Document doc = Jsoup.parse(htmlTxt, "UTF-8");
        Elements elementsWordContent = doc.getElementsByClass("word");
        Elements elementsKeyword = doc.getElementsByClass("keyword");
//        System.out.println("elementsKeyword："+elementsKeyword.text());
        Elements elementsWordShape = doc.getElementsByClass("shape");
//        System.out.println("变形："+elementsWordShape.text());
        String wordShape = "";
        if (elementsWordShape.size() > 0 ) {
            Elements sElementShapeLinks = elementsWordShape.get(0).getElementsByTag("a");
            wordShape = sElementShapeLinks.text();
        }
//        System.out.println("sElementLinks："+sElementLinks.text());
        Elements elementsDictBasic = doc.getElementsByClass("dict-basic-ul");
        Element elementsDictChart = doc.getElementById("dict-chart-basic");//单词me没有本属性
        String pickWordMeaning = "";
        if (elementsDictChart != null && elementsDictChart.hasAttr("data")) {
            String chartData =  elementsDictChart.attr("data");
            //System.out.println("text："+chartData);
            String wordMeaning = decodeDictWord(chartData);
            pickWordMeaning = pickDictWordMeaning(wordMeaning);
        }
//        System.out.println("wordMeaning："+wordMeaning);
        // System.out.println("elementsDictBasic,text："+elementsDictBasic.text());
        // Elements sElementLi = elementsDictBasic.get(0).getElementsByTag("li");
        // System.out.println("li`s,text："+sElementLi.text());
        String [] wordContent = {elementsKeyword.text(),wordShape.replaceAll(" ", "/"),elementsDictBasic.text(),pickWordMeaning};
        return wordContent;
        //return String.join("\t",wordContent);
    }
    
    //dict.cn,解码词义及对应的百分比
    //"%7B%221%22%3A%7B%22percent%22%3A37%2C%22sense%22%3A%22%5Cu810f%5Cu7684%22%7D%2C%222%22%3A%7B%22percent%22%3A27%2C%22sense%22%3A%22%5Cu80ae%5Cu810f%5Cu7684%22%7D%2C%223%22%3A%7B%22percent%22%3A15%2C%22sense%22%3A%22%5Cu5f04%5Cu810f%22%7D%2C%224%22%3A%7B%22percent%22%3A8%2C%22sense%22%3A%22%5Cu5351%5Cu9119%5Cu7684%22%7D%2C%225%22%3A%7B%22percent%22%3A7%2C%22sense%22%3A%22%5Cu4e0b%5Cu6d41%5Cu7684%22%7D%2C%226%22%3A%7B%22percent%22%3A3%2C%22sense%22%3A%22%5Cu73b7%5Cu6c61%22%7D%2C%227%22%3A%7B%22percent%22%3A2%2C%22sense%22%3A%22%5Cu4e0d%5Cu6b63%5Cu5f53%5Cu7684%22%7D%2C%228%22%3A%7B%22percent%22%3A1%2C%22sense%22%3A%22%5Cu8150%5Cu8d25%5Cu7684%22%7D%7D";
    public static String decodeDictWord(String encoded) {
        try {
            //String name=java.net.URLEncoder.encode("测试", "UTF-8");
            //System.out.println(name);
            //System.out.println(java.net.URLDecoder.decode(name, "UTF-8"));
            String decoded = java.net.URLDecoder.decode(encoded, "GBK");
            //System.out.println(decoded);
            String unicode = Utils.convertUnicode(decoded);
            //System.out.println(unicode);
//            System.out.println(URLDecoder.decode(encoded));
            //.getBytes("GBK")
            //String str = new String(convertUnicode(decoded));
            //System.out.println(str);
            return unicode;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    //dict.cn,解码词义及对应的百分比,获取累计占80%的词义，舍弃剩余的20%
    //"%7B%221%22%3A%7B%22percent%22%3A37%2C%22sense%22%3A%22%5Cu810f%5Cu7684%22%7D%2C%222%22%3A%7B%22percent%22%3A27%2C%22sense%22%3A%22%5Cu80ae%5Cu810f%5Cu7684%22%7D%2C%223%22%3A%7B%22percent%22%3A15%2C%22sense%22%3A%22%5Cu5f04%5Cu810f%22%7D%2C%224%22%3A%7B%22percent%22%3A8%2C%22sense%22%3A%22%5Cu5351%5Cu9119%5Cu7684%22%7D%2C%225%22%3A%7B%22percent%22%3A7%2C%22sense%22%3A%22%5Cu4e0b%5Cu6d41%5Cu7684%22%7D%2C%226%22%3A%7B%22percent%22%3A3%2C%22sense%22%3A%22%5Cu73b7%5Cu6c61%22%7D%2C%227%22%3A%7B%22percent%22%3A2%2C%22sense%22%3A%22%5Cu4e0d%5Cu6b63%5Cu5f53%5Cu7684%22%7D%2C%228%22%3A%7B%22percent%22%3A1%2C%22sense%22%3A%22%5Cu8150%5Cu8d25%5Cu7684%22%7D%7D";
    //the
    //{"1":{"percent":28,"sense":"这"},"2":{"percent":27,"sense":"那"},"3":{"percent":18,"sense":"那些"},"4":{"percent":18,"sense":"这些"},"5":{"percent":9,"sense":"用于最高级前"}}
    public static String pickDictWordMeaning(String wordString) {
        String wordMeanings = "";
        int totalPercent = 0;
//        List<PercetWord> percetWordList = FastJsonUtil.json2list(wordString, PercetWord.class);
//        for (PercetWord word :percetWordList) {
//            wordMeanings += word.getSpelling();
//            percent += word.getPercent();
//            if (percent >= 80) {
//                return wordMeanings;
//            }
//        }

        try {
        JSONObject jsonWord = JSONObject.parseObject(wordString);// underutilized的解释中有英文双引号
        for (int i = 1; i <= 4 && jsonWord.containsKey(i + "") && totalPercent < 90 ; i++) {
            JSONObject percentItem = jsonWord.getJSONObject(i + "");
            int curItemPercent = percentItem.getIntValue("percent");
            totalPercent += curItemPercent;
            String curItemMeaning = percentItem.getString("sense");
            if ("".equals(wordMeanings)) {
                wordMeanings = curItemMeaning;
            } else {
                wordMeanings += "," + curItemMeaning;
            }
        }
        }catch (JSONException e) {
            System.out.println("wordString="+wordString);
            e.printStackTrace();
        }
        return wordMeanings;
    }

    //在dict.cn星级词汇列表文件如base3-5.html中，解析href单词
    public static String parseDictHubMain(String htmlTxt) {
        //Document doc = Jsoup.parse(htmlTxt);
        Document doc = Jsoup.parse(htmlTxt, "UTF-8");
//        Elements elements = doc.getElementsByClass("hub-main");//change-clearfix //in-base
        Elements elementsGroups = doc.getElementsByClass("hub-detail-group");//change-clearfix //in-base
//        for (Element e : esBaseLevel) {
//            System.out.println("text："+e.text());
//            Elements sElement = e.getElementsByTag("span");
//            System.out.println("html："+sElement.html());
//            //return sElement.html().replaceAll("&nbsp;", "").replaceAll("\\s", "");
//        }
      //获取迭代器 
        //Iterator it = elements.iterator();
        
        Iterator it = elementsGroups.iterator();
        while(it.hasNext()) {  
            Element element = (Element)it.next();
            //System.out.println("text："+element.text());
            Elements sElementLinks = element.getElementsByTag("a");
            //Elements links = doc.select("a[href]");
            for (Element link : sElementLinks) {  
//                print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
                //System.out.println("href："+link.attr("href").replace("/", ""));
                String spelling = link.attr("href").replace("/", "");
//                System.out.println(spelling);
                ResourceUtil.writerFile(Constant.PATH_RESOURCES + "/vocabulary_dict_star.txt", spelling, true);
//                System.out.println("text："+link.text());
            }  
            
//            System.out.println("text："+sElementLinks.text());
//            String linkHref = sElementLinks.attr("href");
//            System.out.println("linkHref："+linkHref);
//            //String linkText = sElementLink.text(); // "example""//取得链接地址中的文本

            //遍历中。。。。。。 
        }   
        return "";
    }
    
    
    
    public static void main(String[] args) {
        String wordFile = "D:/make_dict.html";
//        String wordFile = "D:/base5-0.html";
        //String level = parseBaseLevel(FileUtil.readFile(wordFile));
        //System.out.println("level:" + level);
        
        //String shape = parseChangeShape(FileUtil.readFile(wordFile));
        //System.out.println("shape:" + shape);


        String []wordCotent = parseDictWordContent(FileUtil.readFile3(wordFile));
        System.out.println(Arrays.toString(wordCotent));  
//        System.out.println(wordCotent);
        //getElement();
        System.out.println("done!");
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
    
    //变形：过去式： took 过去分词： taken 现在分词： taking 第三人称单数： takes
    public static String parseChangeShape(String htmlTxt) {
        String regExPos = "(过去式|过去分词|现在分词|第三人称单数)";
        //Document doc = Jsoup.parse(htmlTxt);
        Document doc = Jsoup.parse(htmlTxt, "UTF-8");
        Elements esBaseLevel = doc.getElementsByClass("word");//change-clearfix //in-base
                                   
        for (Element e : esBaseLevel) {
            // System.out.println("level级别："+e.text());
            Pattern pPos = Pattern.compile(regExPos);
            Matcher matcherPos = pPos.matcher(e.text());
            if (matcherPos.find()) {
                Elements sElement = e.getElementsByTag("span");
                return sElement.html().replaceAll("&nbsp;", "").replaceAll("\\s", "");
            }
        }
        return "";
    }
}
