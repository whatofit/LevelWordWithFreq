/**
 * 从配置文件中读取wordsFile列表
 * 若读取不到，则使用默认WordsFile值
 */
package com.myblog.freq;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.myblog.Constant;
import com.myblog.dao.WordDaoImpl;
import com.myblog.model.Word;
import com.myblog.util.ResourceUtil;

/**
 * @author Dave Fan
 *
 */
public class DumpFreq2WordDB {
    private static final Logger LOGGER = LoggerFactory.getLogger(DumpFreq2WordDB.class);

    private static List<List<String>> mStageLevelList = new ArrayList<List<String>>();

    /**
     * 
     */
    public DumpFreq2WordDB() {
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
            System.out.println("parse word file: " + Constant.PATH_STAGE_FILES + fileName);
            List<String> words = ResourceUtil.readFileLines(Constant.PATH_STAGE_FILES + fileName);
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

    public static int doInsert2DB(Vector<Word> vecWords) {
        ConnectionSource connectionSource;
        try {
            System.out.println("doInsert2DB,URL_DATABASE=" + Constant.URL_DATABASE);
            connectionSource = new JdbcConnectionSource(Constant.URL_DATABASE);
            WordDaoImpl wordDao = new WordDaoImpl(connectionSource);
            int affectRowCount = wordDao.createOrUpdate(vecWords, Word.FIELD_NAME_FREQUENCY);
            // int affectRowCount = wordDao.create(vecWords);
            System.out.println("affectRowCount=" + affectRowCount);
            return affectRowCount;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("AddStageByWordsFile,main,args.length: " + args.length);
        mStageLevelList = ResourceUtil.readStringList(Constant.FILE_STAGE_WORDS_FILES);
        // System.out.println("AddStageByWordsFile,main,stageFileList: " +
        // mStageLevelList);
        Map<String, Map<String, String>> mapResult = getWordList(mStageLevelList);// 先获取level比较低的单词集合，后获取levle较高的集合
        System.out.println("all unique words count: " + mapResult.size());
        Vector<Word> vecWords = map2vector(mapResult);
        doInsert2DB(vecWords);
    }
}
