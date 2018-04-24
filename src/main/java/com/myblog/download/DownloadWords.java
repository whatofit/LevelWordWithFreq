package com.myblog.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myblog.Constant;
import com.myblog.freq.DumpFreq2WordDB;
import com.myblog.model.Word;
import com.myblog.util.FileUtil;
import com.myblog.util.HttpUtil;
import com.myblog.util.ResourceUtil;

//下载在线词典,如：
//http://dict.cn/review
//http://www.iciba.com/dirty
public class DownloadWords {
    private static final Logger LOGGER = LoggerFactory.getLogger(DumpFreq2WordDB.class);

    private static List<List<String>> mStageLevelList = new ArrayList<List<String>>();

    public DownloadWords() {
    }

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
    public static Map<String, Map<String, String>> getWordList(List<List<String>> stageLevelList) {
        // Set<Word> setResult = new HashSet<>();
        // Map<String, String> map = new HashMap<String, String>();
        // word,courseLevel,0/1/2/3
        Map<String, Map<String, String>> mapResult = Collections
                .synchronizedMap(new HashMap<String, Map<String, String>>());
        for (List<String> curStageFile : stageLevelList) {
            String curStage = curStageFile.get(0);
            String fileName = curStageFile.get(1);
            System.out.println("parse word file: " + Constant.PATH_RESOURCES + fileName);
            List<String> words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + fileName);
            for (String line : words) {// 遍历set去出里面的的Key
                // 用"-2"替代"★",用"-3"替代"▲",把左右小括号()删除掉，把斜线/之后的字符串删除(到行尾)
                line = line.trim().replaceAll("★", "-2").replaceAll("▲", "-3").replaceAll("/.*$", "");
                if (!line.startsWith("#") && !"".equals(line)) {
                    String[] segments = line.split("\\s+");// 以空白(空格/tab键/回车/换行)分割
                    String word = "";
                    String courseValue = "1";
                    if (segments.length <= 1) {
                        word = segments[0];
                    } else {
                        word = segments[1];
                        if (segments[0].equals("-2")) {
                            courseValue = "2";
                        } else if (segments[0].equals("-3")) {
                            courseValue = "3";
                        } else {
                            courseValue = "1";
                        }
                    }
                    word = word.replaceAll("[()\\d]", "");// 去掉序号
                    Map<String, String> mapWord = mapResult.get(word);
                    if (mapWord == null) {
                        mapWord = new HashMap<String, String>();
                        mapWord.put(curStage, courseValue);
                        mapResult.put(word, mapWord); // 新增
                    } else {
                        mapWord.put(curStage, courseValue); // 修改
                    }
                }
            }

            System.out.println("wordSet count: " + words.size());
        }
        System.out.println("unique words count: " + mapResult.size());
        return mapResult;
    }

    /**
     * 根据《American National Corpus,ANC.txt》词汇列表保存到数据库
     * 
     * ,ANC.txt文件格式:每行2列，第1列：词频；第2列：单词；
     * 
     * 中间用空格/空白隔开、
     * 
     * @return Vector<Word>
     * 
     */
    public static Vector<Word> map2vector(Map<String, Map<String, String>> mapResult) {
        Vector<Word> vecWords = new Vector<Word>();
        try {
            System.out.println("read word file: " + Constant.FILE_FREQ_OF_WORDS);
            List<String> words = ResourceUtil.readFileLines(Constant.FILE_FREQ_OF_WORDS);
            for (int i = 0; i < words.size(); i++) {
                // 用"-2"替代"★",用"-3"替代"▲",把左右小括号()删除掉，把斜线/之后的字符串删除(到行尾)
                String line = words.get(i).trim();
                if (!line.startsWith("#") && !"".equals(line)) {
                    String[] segments = line.split("\\s+");// 以空白(空格/tab键/回车/换行)分割
                    String word = "";
                    String freq = "99999";// 默认较大的频率
                    if (segments.length <= 1) {
                        word = segments[0];
                    } else {
                        word = segments[1];
                        freq = segments[0];
                    }
                    // System.out.println("freq,word:" + freq + "\t" + word);
                    StringBuilder sb = new StringBuilder();
                    // sb.append(freq + "\t" + word + "\t");
                    Map<String, String> mapWord = mapResult.get(word);
                    if (mapWord != null) {
                        for (int j = 0; j < mStageLevelList.size(); j++) {
                            String stageName = mStageLevelList.get(j).get(0);
                            String courseValue = mapWord.get(stageName);
                            courseValue = courseValue == null ? "" : courseValue;
                            sb.append(new String(courseValue) + "\t");
                        }
                        mapResult.remove(word);
                    }
                    Word dbWord = new Word();
                    dbWord.setSpelling(word);
                    // Integer nFreq = Integer.parseInt(freq);
                    dbWord.setFrequency(freq);
                    dbWord.setLevel(sb.toString());
                    vecWords.add(dbWord);
                }
            }
            System.out.println("leave words count: " + mapResult.size());
            for (Iterator it = mapResult.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry2 = (Map.Entry) it.next();
                String word = (String) entry2.getKey();
                Map<String, String> mapWord = (Map<String, String>) entry2.getValue();
                StringBuilder sb = new StringBuilder();
                // sb.append("99999" + "\t" + word + "\t");
                for (int j = 0; j < mStageLevelList.size(); j++) {
                    String stageName = mStageLevelList.get(j).get(0);
                    String courseValue = mapWord.get(stageName);
                    courseValue = courseValue == null ? "" : courseValue;
                    sb.append(courseValue + "\t");
                }
                Word dbWord = new Word();
                dbWord.setSpelling(word);
                dbWord.setFrequency("99999");
                dbWord.setLevel(sb.toString());
                vecWords.add(dbWord);
            }
            // LOGGER.info("保存成功");
        } catch (Exception e) {
            LOGGER.error("保存词典失败", e);
        }
        return vecWords;
    }

    /**
     * 查看统计的词汇表在《American National Corpus,ANC.txt》词汇列表中的分布
     * 
     * ,ANC.txt文件格式:每行2列，第1列：词频；第2列：单词；
     * 
     * 中间用空格/空白隔开、
     * 
     * 
     * 
     * @return Vector<Word>
     * 
     */
    public static Vector<Word> removeVectorByMap(Map<String, Map<String, String>> mapResult) {
        Vector<Word> vecWords = new Vector<Word>();
        try {
            System.out.println("read word file: " + Constant.FILE_FREQ_OF_WORDS);
            List<String> words = ResourceUtil.readFileLines(Constant.FILE_FREQ_OF_WORDS);
            for (int i = 0; i < words.size(); i++) {
                // 用"-2"替代"★",用"-3"替代"▲",把左右小括号()删除掉，把斜线/之后的字符串删除(到行尾)
                String line = words.get(i).trim();
                if (!line.startsWith("#") && !"".equals(line)) {
                    String[] segments = line.split("\\s+");// 以空白(空格/tab键/回车/换行)分割
                    String word = "";
                    String freq = "99999";// 默认较大的频率
                    if (segments.length <= 1) {
                        word = segments[0];
                    } else {
                        word = segments[1];
                        freq = segments[0];
                    }
                    // System.out.println("freq,word:" + freq + "\t" + word);
                    StringBuilder sb = new StringBuilder();
                    // sb.append(freq + "\t" + word + "\t");
                    Map<String, String> mapWord = mapResult.get(word);
                    if (mapWord != null) {
                        for (int j = 0; j < mStageLevelList.size(); j++) {
                            String stageName = mStageLevelList.get(j).get(0);
                            String courseValue = mapWord.get(stageName);
                            courseValue = courseValue == null ? "" : courseValue;
                            sb.append(new String(courseValue) + "\t");
                        }
                        mapResult.remove(word);
                    }
                    Word dbWord = new Word();
                    dbWord.setSpelling(word);
                    // Integer nFreq = Integer.parseInt(freq);
                    dbWord.setFrequency(freq);
                    dbWord.setLevel(sb.toString());
                    vecWords.add(dbWord);
                }
            }
            System.out.println("leave words count: " + mapResult.size());
            for (Iterator it = mapResult.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry2 = (Map.Entry) it.next();
                String word = (String) entry2.getKey();
                Map<String, String> mapWord = (Map<String, String>) entry2.getValue();
                StringBuilder sb = new StringBuilder();
                // sb.append("99999" + "\t" + word + "\t");
                for (int j = 0; j < mStageLevelList.size(); j++) {
                    String stageName = mStageLevelList.get(j).get(0);
                    String courseValue = mapWord.get(stageName);
                    courseValue = courseValue == null ? "" : courseValue;
                    sb.append(courseValue + "\t");
                }
                Word dbWord = new Word();
                dbWord.setSpelling(word);
                dbWord.setFrequency("99999");
                dbWord.setLevel(sb.toString());
                vecWords.add(dbWord);
            }
            // LOGGER.info("保存成功");
        } catch (Exception e) {
            LOGGER.error("保存词典失败", e);
        }
        return vecWords;
    }

    public static String parseBaseLevel(String htmlTxt) {
        String regExPos = "(CET4|CET6|考研|IELTS|TOEFL|GRE)";
        Document doc = Jsoup.parse(htmlTxt);
        Elements esBaseLevel = doc.getElementsByClass("base-level");
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

    public static void main(String[] args) {
        // http://dict.cn/review
        // https://cn.bing.com/dict/search?q=dirty
        // http://www.iciba.com/dirty
        // http://dict.youdao.com/w/dirty

        // String url = "http://dict.cn";

        // String para = "";
        // String sr = HttpUtil.sendPost(url, para, false);
        // System.out.println(sr);

        /*
         * mStageLevelList =
         * ResourceUtil.readStringList(Constant.FILE_STAGE_WORDS_FILES,"#");
         * System.out.println("DownloadDict,main,stageFileList: " +
         * mStageLevelList); Map<String, Map<String, String>> mapResult =
         * getWordList(mStageLevelList);// 先获取level比较低的单词集合，后获取levle较高的集合
         * System.out.println("all unique words count: " + mapResult.size());
         * Vector<Word> vecWords = map2vector(mapResult); // 遍历Vector中的元素 for
         * (int i = 0; i < vecWords.size(); i++) { Word word = vecWords.get(i);
         * String spelling = word.getFrequency() +"\t"+ word.getSpelling();
         * //System.out.println(i + ","+ spelling); //String sr =
         * HttpUtil.sendPost(url+"/"+spelling, null, false);
         * //ResourceUtil.writerFile(Constant.FOLDER_VOCABULARY_DICT+File.
         * separator+spelling+".html", sr,false);
         * ResourceUtil.writerFile("all_word.txt", spelling,true); }
         */

        // //读取单词列表，下载并过滤考级单词保存
        String url = "http://www.iciba.com";
        List<String> lines = ResourceUtil.readFileLines("examCiba4104.txt");
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (!line.startsWith("#") && !"".equals(line)) {
                String[] segments = line.split("\\s+");// 以空白(空格/tab键/回车/换行)分割
                String spelling = "";
                // String freq = "99999";// 默认较大的频率
                if (segments.length <= 1) {
                    spelling = segments[0];
                } else {
                    spelling = segments[1];
                    // freq = segments[0];
                }
                String sr = HttpUtil.sendPost(url + "/" + spelling, null, false);// spelling.toLowerCase()
                String level = parseBaseLevel(sr);
                if (!"".equals(level)) {
                    ResourceUtil.writerFile(Constant.FOLDER_CIBA + File.separator + spelling + ".html", sr,
                            false);
                    ResourceUtil.writerFile("all_word_tmp.txt", spelling + "\t" + level, true);
                }
            }
        }
        System.out.println("done!");

        // String spelling = "rabbit";
        // String sr = HttpUtil.sendPost(url+"/"+spelling, null,
        // false);//spelling.toLowerCase()
        // String level = parseBaseLevel(sr);
        // if (!"".equals(level)){
        // ResourceUtil.writerFile(Constant.FOLDER_VOCABULARY_CIBA2+File.separator+spelling+".html",
        // sr,false);
        // ResourceUtil.writerFile("all_word_temp.txt", spelling + "\t" +
        // level,true);
        // }

    }
}
