/**
 * 从配置文件中读取wordsFile列表
 * 若读取不到，则使用默认WordsFile值
 */
package com.myblog.level;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.myblog.dao.WordDaoImpl;
import com.myblog.model.Word;

/**
 * @author Dave Fan
 *
 */
public class NewOrUpdateWordLevel {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewOrUpdateWordLevel.class);
    private static String mUserDir = System.getProperty("user.dir");
    // url = DumpANC2FreqWordDB.class.getResource("");
    // file:/E:/workspace_4.6.3_LevelWord_2017-07-26/LevelWordWithFreq/bin/com/myblog/freq/
    // url = DumpANC2FreqWordDB.class.getResource("/");
    // file:/E:/workspace_4.6.3_LevelWord_2017-07-26/LevelWordWithFreq/bin/
    // realPath=java.net.URLDecoder.decode(realPath,"utf-8");
    private final static String mStageFileList = "./input/stageWordsFiles.txt";
    private static List<List<String>> mStageLevelList = new ArrayList<List<String>>();

    /**
     * 
     */
    public NewOrUpdateWordLevel() {
    }

    // 根据mStageFileList中单词的顺序,读取input文件夹下的txt文件列表
    public static List<List<String>> loadStagesFiles(String stagesFiles) {
        List<List<String>> stageLevelList = new ArrayList<List<String>>();
        try {
            FileInputStream fis = new FileInputStream(stagesFiles);
            BufferedReader dr = new BufferedReader(new InputStreamReader(fis));
            String line = dr.readLine();
            while (line != null) {
                String[] split = line.split("\\s+");
                List<String> lineStage = new ArrayList<String>(Arrays.asList(split));
                stageLevelList.add(lineStage);
                line = dr.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stageLevelList;
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
            URI uri = null;
            try {
                if (fileName.startsWith("/")) {// 相对路径
                    uri = Paths.get(mUserDir + "/input" + fileName).toUri();
                } else {
                    uri = Paths.get(fileName).toUri();// 绝对路径(windows)
                }
            } catch (Exception e) {
                LOGGER.error("构造URL出错", e);
            }
            if (uri == null) {
                LOGGER.error("解析词典失败：" + fileName);
                continue;
            }
            System.out.println("parse word file: " + uri);
            List<String> words = readFileLines(uri);
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

    private static List<String> readFileLines(URI uri) {
        try {
            return Files.readAllLines(Paths.get(uri));
        } catch (Exception e) {
            return Collections.emptyList();
        }
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
    public static void save(Map<String, Map<String, String>> mapResult, String path) {
        String fileANC = "/American National Corpus,ANC.txt";
        List<String> wordList = new ArrayList<String>();
        try {
            URI uri = Paths.get(mUserDir + "/input" + fileANC).toUri();
            if (uri == null) {
                LOGGER.error("解析词典失败：" + fileANC);
                return;
            }
            System.out.println("parse word file: " + uri);
            List<String> words = readFileLines(uri);
            path = Paths.get(mUserDir + "/output" + path).toString();// 父目录必须存在
            LOGGER.info("开始保存词典：" + path);

            StringBuilder sbTitle = new StringBuilder();
            sbTitle.append("freq\tword\t");
            for (int j = 0; j < mStageLevelList.size(); j++) {
                String stageName = mStageLevelList.get(j).get(0);
                sbTitle.append(stageName + "\t");
            }
            wordList.add(sbTitle.toString());

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
                    StringBuilder sb = new StringBuilder();
                    sb.append(freq + "\t" + word + "\t");
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
                    wordList.add(sb.toString());
                }
            }
            System.out.println("leave words count: " + mapResult.size());
            for (Iterator it = mapResult.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry2 = (Map.Entry) it.next();
                String word = (String) entry2.getKey();
                Map<String, String> mapWord = (Map<String, String>) entry2.getValue();
                StringBuilder sb = new StringBuilder();
                sb.append("99999" + "\t" + word + "\t");
                for (int j = 0; j < mStageLevelList.size(); j++) {
                    String stageName = mStageLevelList.get(j).get(0);
                    String courseValue = mapWord.get(stageName);
                    courseValue = courseValue == null ? "" : courseValue;
                    sb.append(courseValue + "\t");
                }
                wordList.add(sb.toString());
            }
            Files.write(Paths.get(path), wordList);
            LOGGER.info("保存成功");
        } catch (Exception e) {
            LOGGER.error("保存词典失败", e);
        }
        // System.out.println("unique words count: "+set.size());
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
        String fileANC = "/American National Corpus,ANC.txt";
        // List<String> wordList = new ArrayList<String>();
        Vector<Word> vecWords = new Vector<Word>();
        try {
            URI uri = Paths.get(mUserDir + "/input" + fileANC).toUri();
            if (uri == null) {
                LOGGER.error("解析词典失败：" + fileANC);
                return vecWords;
            }
            System.out.println("parse word file: " + uri);
            List<String> words = readFileLines(uri);

            // String path = Paths.get(mUserDir + "/output" +
            // filename).toString();// 父目录必须存在
            // LOGGER.info("开始保存词典：" + path);
            //
            // StringBuilder sbTitle = new StringBuilder();
            // sbTitle.append("freq\tword\t");
            // for (int j = 0; j < mStageLevelList.size(); j++) {
            // String stageName = mStageLevelList.get(j).get(0);
            // sbTitle.append(stageName + "\t");
            // }
            // wordList.add(sbTitle.toString());

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
                    dbWord.setFrequency(freq);
                    dbWord.setLevel(sb.toString());
                    vecWords.add(dbWord);
                    // wordList.add(sb.toString());
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
                // wordList.add(sb.toString());
            }
            // Files.write(Paths.get(path), wordList);
            LOGGER.info("保存成功");
        } catch (Exception e) {
            LOGGER.error("保存词典失败", e);
        }
        // System.out.println("unique words count: "+set.size());
        return vecWords;
    }

    public static int doInsert2DB(Vector<Word> vecWords) {
        ConnectionSource connectionSource;
        try {
            // String databaseUrl = "jdbc:h2:./LevelDict.h2";
            String databaseUrl = "jdbc:sqlite:./output/LevelDict.db3";
            connectionSource = new JdbcConnectionSource(databaseUrl);
            WordDaoImpl wordDao = new WordDaoImpl(connectionSource);
            int affectRowCount = wordDao.create(vecWords);
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
        System.out.println("DumpANC2FreqWordDB,mUserDir:" + mUserDir);
        System.out.println("AddStageByWordsFile,main,args.length: " + args.length);
        mStageLevelList = loadStagesFiles(mStageFileList);
        // System.out.println("AddStageByWordsFile,main,stageFileList: " +
        // Arrays.toString(stageFileList));
        System.out.println("AddStageByWordsFile,main,stageFileList: " + mStageLevelList);

        Map<String, Map<String, String>> mapResult = getWordList(mStageLevelList);// 先获取level比较低的单词集合，后获取levle较高的集合
        System.out.println("all unique words count: " + mapResult.size());
        // String toSaveFile = "/toSaveLevelFile.txt";
        // save(mapResult, toSaveFile);
        Vector<Word> vecWords = map2vector(mapResult);
        doInsert2DB(vecWords);
    }
}
