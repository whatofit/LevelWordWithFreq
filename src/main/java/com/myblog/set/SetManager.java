package com.myblog.set;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myblog.Constant;
import com.myblog.model.Word;
import com.myblog.util.CfgUtil;

public class SetManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(SetManager.class);
    
    public SetManager() {
    }

    public static void main(String[] args) {
        long startTime =System.currentTimeMillis();
        // 1.读取两组单词集合路径
        String minuend_files = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "minuend_files");
        String subtrahend_files = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "subtrahend_files");
        // 2.分别计算两组集合的并集
        Set<Word> set1 = WordFilesMgr.loadWord("", minuend_files.split(","));
        Set<Word> set2 = WordFilesMgr.loadWord("", subtrahend_files.split(","));
        // 3.C=A-B
        Set<Word> setResult = WordSetminus(set1, set2);
        // 4.保存结果集合C到文件
        String result_set_minus = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "resultSetFile");
        WordFilesMgr.saveWordSet(setResult, Constant.PATH_RESOURCES + result_set_minus);
        
        long endTime =System.currentTimeMillis();
        System.out.println("执行耗时 : "+(endTime-startTime)/1000f+" 秒 ");
    }

    public static Set<Word> WordSetminus(Set<Word> minuend, Set<Word> subtrahend) {
        LOGGER.info("被减数个数：" + minuend.size());
        LOGGER.info("减数个数：" + subtrahend.size());
        Set<Word> result = minuend.stream().filter(word -> !subtrahend.contains(word)).distinct()
                .collect(Collectors.toSet());

        // Set<Word> result = minuend
        // .stream()
        // .filter(word -> subtrahend.contains(word))
        // .collect(Collectors.toSet());

        // //先转换成list，再排序
        // List<Word> wordList = new ArrayList<Word>(result);
        // Collections.sort(wordList, new Comparator<Word>() {
        // public int compare(Word arg0, Word arg1) {
        // int freq0 = arg0.getFrequency();
        // int freq1 = arg1.getFrequency();
        // if (freq0 < freq1)
        // return 1;
        // else if (freq0 > freq1)
        // return -1;
        // else
        // return 0;
        // }
        // });

        // Set<Word> result = new HashSet<Word>();
        // result.clear();
        // result.addAll(minuend);
        // result.removeAll(subtrahend);
        LOGGER.info("结果个数：" + result.size());
        return result;
    }

    /**
     * 求交集
     * 
     * @param first
     * @param second
     * @return
     */
    public static Set<Word> setIntersection(Set<Word> first, Set<Word> second) {
        LOGGER.info("求交集词典1：" + first.size());
        LOGGER.info("求交集词典2：" + second.size());
        // Set<Word> result = first
        // .stream()
        // .filter(w -> second.contains(w))
        // .collect(Collectors.toSet());

        Set<Word> result = new HashSet<Word>();
        result.clear();
        result.addAll(first);
        result.retainAll(second); // 交集

        LOGGER.info("交集词典：" + result.size());
        return result;
    }

    /**
     * 求差集
     * 
     * @param first
     * @param second
     * @return
     */
    public static Set<Word> setDifference(Set<Word> first, Set<Word> second) {
        LOGGER.info("求差集词典1：" + first.size());
        LOGGER.info("求差集词典2：" + second.size());

        Set<Word> result = new HashSet<Word>();
        result.clear();
        result.addAll(first);
        result.removeAll(second);
        System.out.println("差集：" + result);

        LOGGER.info("差集词典：" + result.size());
        return result;
    }

    /**
     * 求并集
     * 
     * @param first
     * @param second
     * @return
     */
    public static Set<Word> setUnion(Set<Word> first, Set<Word> second) {
        LOGGER.info("求并集词典1：" + first.size());
        LOGGER.info("求并集词典2：" + second.size());

        Set<Word> result = new HashSet<Word>();
        result.clear();
        result.addAll(first);
        result.addAll(second);
        System.out.println("并集：" + result);

        LOGGER.info("并集词典：" + result.size());
        return result;
    }
    
//    public static void main(String[] args) {
////    AtomicInteger i = new AtomicInteger();
////    stem(getSyllabusVocabulary()).forEach(w -> System.out.println(i.incrementAndGet() + "、" + w.getWord()));
//    
////    String html = HtmlFormatter.toHtmlForPluralFormat(plural(getSyllabusVocabulary()));
////    System.out.println(html);
//
//  
//  String file1 = "";
//  String file2 = "";
//  String toLevel = ""; //要读取的文件，是某分级
//  String toSaveFile = "";
////    file1 = "/CollegeRequirements4783一般要求-全小写.txt";     //一般要求
////    file1 = "/CollegeRequirements1579仅6级-全小写.txt";      //仅-较高要求
////    file1 = "/CollegeRequirements1268-仅更高要求-全小写.txt";//仅-更高要求
////    file1 = "/CollegeRequirements6362-全较高要求-全小写.txt";// 全-较高要求
////    file1 = "/CollegeRequirements7629全-全小写.txt";        //全-全小写.txt
//  
////    file1 = "/word_primary_school.txt"; //小学
////    file1 = "/word_junior_school.txt";  //初中
////    file1 = "/word_senior_school.txt";  //高中
////    file1 = "/word_university.txt";     //大学
////    toLevel ="primary_school";
////    toLevel ="junior_school";
////    toLevel ="word_senior_school";
////    toLevel ="word_university";
//  
////    String []files1 ={"/word_ADULT.txt", "/word_BEC.txt", "/word_CATTI.txt", "/word_CET4.txt", "/word_CET6.txt", "/word_GMAT.txt", "/word_GRE.txt", "/word_IELTS.txt", "/word_junior_school.txt", "/word_MBA.txt", "/word_new_conception.txt", "/word_primary_school.txt", "/word_SAT.txt", "/word_senior_school.txt", "/word_TEM4.txt", "/word_TEM8.txt", "/word_TOEFL.txt", "/word_TOEIC.txt", "/word_university.txt", "/word_考 研.txt"};
////    String []files2 ={"/word_primary_school.txt","/word_junior_school.txt","/word_senior_school.txt","/word_university.txt"};
////    String []files3 ={"/word_IELTS.txt","/word_TOEFL.txt","/word_GRE.txt","/word_SAT.txt","/word_GMAT.txt"};
//  
//  toSaveFile = "/toSaveFile_removeAll.txt";
////    toSaveFile = "/minus3.txt";
////    List<String> lst1 = new ArrayList<String>();
//  Set<Word> set1 = new HashSet<>();
//  Set<Word> set2 = new HashSet<>();
//  Set<Word> set3 = new HashSet<>();
//  Set<Word> setResult = new HashSet<>();
////    Set<Word> words = new HashSet<>();
//  //words.addAll(get2());
////    words.addAll(set1);
//  
////    set1 = getCollegeRequirements2Level();
////    set1 = getCollegeRequirements(4);//获取《大学英语课程教学要求.txt》词汇
////    set1 = getCollegeRequirements();
//  
////    file1 = "/大写单词.txt";
////    file2 = "/小写单词.txt";
////    set1 = getWordList(file1); 
////    set2 = getWordFreqList(files2); 
//  
//  set1 = getWordFreqList("E:/workspace_4.6.3_LevelWord_2017-07-26/LevelWordWithFreq/src/main/resources/American National Corpus,ANC.txt");
//
////    set1 = get("","/word_CET4.txt"); //CET4词汇
////    set2 = get("","/word_CET6.txt"); //CET6词汇
////    set3 = get("","/word_考 研.txt"); //考 研
////    set2 = get("/word_university.txt");
////    set1 = get("/word_IELTS.txt");
////    set1 = get(toLevel, files1);
////    set1 = get("/American National Corpus,ANC.txt");
////    set2.retainAll(set1);//求交集
////    set3.removeAll(set1); //先把当前集合(set1)中和结果集(setResult)重复的的单词全部除去；
////    setResult.addAll(set2);//再把修剪过的当前集合(set1)加入到结果集(setResult)中
//  
////    setResult = intersection(set1,set2);
////    setResult = minus(set1,set2);
////    setResult = minus(set1,minus(set2,set1));
////    setResult = mergeWordLevelByFreq(set1,set2);
////    save(set3,toSaveFile);
//  
////    saveList(null,"/freqList.txt");
//
//  //toSaveCourseLevelWord();
//  
////    set1 = get("",Constant.PROJECT_BIN_DIR + "vocabulary_exam.txt");
//  //set2 = get("",Constant.PROJECT_BIN_DIR + "levelsWord_ciba2.txt");
//  //set1 = get("",Constant.PROJECT_BIN_DIR + "stageFiles/word_Graduate_Entrance_Exam5500.txt");
//  set2 = get("",Constant.PROJECT_BIN_DIR + "ANC_spoken.txt");
//  setResult = minus(set2,set1);
//  toSaveFile = "/ANC_spoken_minus_ANC.txt";
//    save(setResult,toSaveFile);
//}
}
