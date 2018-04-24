package com.myblog.set;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myblog.Constant;
import com.myblog.model.Word;
import com.myblog.util.RegEx;
import com.myblog.util.ResourceUtil;

public class WordFilesMgr {

    private static final Logger LOGGER = LoggerFactory.getLogger(WordFilesMgr.class);
    
    public WordFilesMgr() {
    }

    public static void main(String[] args) {
    }
    
    //转化,使转化,使改变,使转变
    //字符串key-value(frequency)集合转换成word集合
    public static Map<Word, AtomicInteger> convert(Map<String, AtomicInteger> words) {
        Map<Word, AtomicInteger> result = new HashMap<>();
        words.keySet().forEach(w -> result.put(new Word(w), words.get(w)));
        return result;
    }

    /**
     * 一行一个单词，单词和其他信息之间用空白字符隔开 默认 index 为1
     * 
     * @param files
     *            单词文件类路径，以/开头
     * @return 不重复的单词集合
     */
    public static Set<Word> loadWord(String level, String... files) {
        return loadWord(1, level, files);
    }

    /**
     * 一行一个单词，单词和其他信息之间用空白字符隔开
     * 
     * @param index
     *            单词用空白字符隔开后的索引，从0开始
     * @param files
     *            单词文件类路径，以/开头
     * @return 不重复的单词集合
     */
    public static Set<Word> loadWord(int index, String level, String... files) {
        Set<Word> setResult = new HashSet<>();
        for (String file : files) {
            System.out.println("parse word file: " + file);
            List<String> words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + file);

            // for(String line:words) {
            // String[] spt = line.trim().split("\\s+");
            // Word word = new Word(spt[0],spt[index],level);
            // System.out.println("parse word: "+word);
            // }

//            Set<Word> wordSet = words.parallelStream()
//                    .filter(line -> !line.trim().startsWith("#") &&!"".equals(line.trim()))
//                    // .filter(line -> line.trim().split("\\s+").length >=
//                    // index+1)
//                    // .map(line -> new
//                    // Word(line.trim().toLowerCase().split("\\s+")[index],level))
//                    .map(line -> new Word(line.trim().split("\\s+")[0], line.trim().split("\\s+")[index], level))
//                    // .filter(word ->
//                    // StringUtils.isAlphanumeric(word.getSpelling()))
//                    // //如果testString全由字母或数字组成，返回True
//                    // .filter(word -> !"".equals(word.getSpelling().trim()))
//                    // .min(comparator)
//                    // .map(String::toLowerCase)
//                    // //toLowerCase/toUpperCase把所有的单词转换为大写。
//                    .distinct()
//                    // .sorted()
//                    .collect(Collectors.toSet());
            
            Set<Word> wordSet = words.parallelStream()
                    .filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
                    .map(RegEx::catchNumberWord)
                    //.distinct()
                    .collect(Collectors.toSet());
            
            setResult.addAll(wordSet);
            System.out.println("wordSet count: " + wordSet.size());
        }
        System.out.println("unique words count: " + setResult.size());
        return setResult;
    }
    
    /**
     * 获取单词列表,
     * 文件格式：每行只有单词，没有单词序号或行序号，也没有词频，如下格式：
believe
city
room
under
while
less
group
several
     * @return Set<Word>
     */
    public static Set<Word> loadWordList(String... files){
        Set<Word> set = new HashSet<>();
        for(String file : files){
            System.out.println("parse word file: "+file);
            List<String> words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + file);
            Set<Word> wordSet = words.parallelStream()
                    .filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
                    .map(line -> new Word(line.trim().toLowerCase()))
//                    .map(line -> new Word(line.trim(),"64"))
                    //.filter(word -> StringUtils.isAlphanumeric(word.getWord()))
                    .distinct()
                    .collect(Collectors.toSet());
            set.addAll(wordSet);
            //System.out.println("wordSet count: "+wordSet.size());
        }
        System.out.println("unique words count: "+set.size());
        return set;
    }
    
    /**
     * 获取类似ANC频率单词列表
     * 
     * 文件格式:每行(至少)2列，第1列：词频/序号/行号/level；第2列：单词；
     * 
     * 中间用空格/空白隔开，格式如下：
651 quiet
652 anywhere
653 wear
654 finish
655 mistake
656 ride
657 fault
658 ladies
659 needed
660 quick
661 choice
     * @return Set<Word>
     */
    public static Set<Word> loadWordListOfFreq(String... files){
        Set<Word> set = new HashSet<>();
        for(String file : files){
            
            System.out.println("parse word file: "+file);
            List<String> words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + file);
            Set<Word> wordSet = words.parallelStream()
                    .filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
                    .filter(line -> line.trim().split("\\s+").length >= 2)
                    .map(line -> new Word(line.trim().toLowerCase().split("\\s+")[0], line.trim().toLowerCase().split("\\s+")[1])) 
//                    .map(line -> new Word(line.trim().toLowerCase().split("\\s+")[1]))
                    //.filter(word -> StringUtils.isAlphanumeric(word.getWord()))
                    .distinct() //去重复的值
                    .collect(Collectors.toSet());
            System.out.println("wordSet count: "+wordSet.size());
            set.addAll(wordSet);
        }
        System.out.println("unique words count: "+set.size());
        return set;
    }

    
    /**
     * 获取《大学英语课程教学要求.txt》词汇
     * College English Curriculum Requirements
     * 
     * 文件格式:每行2列，第1列：★或▲或无；第2列：单词；
     * 
     * 中间用空格/空白隔开，无单词序号也无行序号，格式如下：
   access
   accessible
★ accession
★ accessory
   accident
   accidental
▲ acclaim
★ accommodate
   accommodation
   accompany
▲ accomplice
   accomplish
   accord
     * @return Set<Word>
     */
    public static Set<Word> loadWordListOfCollegeRequirements2Level(){
        //String[] files ={"/大学英语课程教学要求.txt"};
        String[] files ={"/stageFiles/word_College_Requirements.txt"};
        
        Set<Word> set = new HashSet<>();
        for(String file : files){
            System.out.println("parse word file: "+file);
            List<String> words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + file);
            Set<Word> wordSet = words.parallelStream()
                    .filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
                    .map(RegEx::toLevelWord)
                    .collect(Collectors.toSet());
            set.addAll(wordSet);
            System.out.println("wordSet count: "+wordSet.size());
        }
        System.out.println("unique words count: "+set.size());
        return set;
    }
    
    /**
     * 获取《大学英语课程教学要求.txt》词汇
     * College English Curriculum Requirements
     * 
     * 文件格式:每行2列，第1列：★或▲或无；第2列：单词；
     * 
     * 中间用空格/空白隔开，无单词序号也无行序号
     * 
     * @return List<String> 
     * 
     */
    //public static List<String> getCollegeRequirements(){
    public static Set<Word> loadWordListOfCollegeRequirements(){
        //String[] files ={"/大学英语课程教学要求.txt"};
        String[] files ={"/stageFiles/word_College_Requirements.txt"};
        
        Set<Word> set = new HashSet<>();
        for(String file : files){
            System.out.println("parse word file: "+file);
            List<String> words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + file);

//            for (int i = 0; i < words.size();i++){
//              String line = words.get(i);
//              wordList.add(line.replaceAll("★", "").replaceAll("▲", "").replaceAll("[()\\d]", "").replaceAll("/.*$", "").trim());
//            }
            
            //更高要求-全部-去重复,//(全转换为小写)
            Set<Word> wordSet5 = words.parallelStream()
                    .filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
                    .map(line -> new Word(line.replaceAll("★", "").replaceAll("▲", "").replaceAll("[()\\d]", "").replaceAll("/.*$", "").trim()))//.toLowerCase()
                    .distinct()
                    .collect(Collectors.toSet());
            System.out.println("words count5: "+wordSet5.size());
            set.addAll(wordSet5);
        }
//        System.out.println("unique words count: "+wordList.size());
//        System.out.println(wordList);
//        Collections.sort(wordList, new Comparator<String>() {
//            public int compare(String arg0, String arg1) {
//                return arg0.compareToIgnoreCase(arg1);
//            }
//        });
//        return wordList;
      System.out.println("unique words count: "+set.size());
      return set;
    }
    
//    /**
//     * 获取《大学英语课程教学要求.txt》词汇
//     * College English Curriculum Requirements
//     * 
//     * 文件格式:每行2列，第1列：★或▲或无；第2列：单词；
//     * 
//     * 中间用空格/空白隔开，无单词序号也无行序号
//     * 
//     * @return Set<Word>
//     */
//    public static Set<Word> loadWordListOfCollegeRequirements(int level){
//        //String[] files ={"/大学英语课程教学要求.txt"};
//        String[] files ={"/word_College_Requirements.txt"};
//        
//        Set<Word> set = new HashSet<>();
//        for(String file : files){
//            System.out.println("parse word file: "+file);
//            List<String> words = ResourceUtil.readFileLines(Constant.FOLDER_STAGE_FILES + file);
//
////          //RegEx.catchWord(line,level)
////          Set<Word> wordSet = words.parallelStream()
////                  .filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
////                  .map(line -> new Word(RegEx.catchWord(line,level)))
////                  .filter(word -> !"".equals(word.getSpelling().trim()))
////                  .filter(word -> StringUtils.isAlphanumeric(word.getSpelling()))
////                  .collect(Collectors.toSet());
//            
//            //4级以下        
//            Set<Word> wordSet1 = words.parallelStream()
//                    .filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
//                    .filter(line -> !line.contains("★") && !line.contains("▲"))
//                    .map(line -> new Word(line.replaceAll("[()\\d]", "").replaceAll("/.*$", "").toLowerCase().trim()))
//                    .collect(Collectors.toSet());
//            
//            //仅6级
//            Set<Word> wordSet2 = words.parallelStream()
//                    .filter(line -> line.contains("★"))
//                    .map(line -> new Word(line.replaceAll("★", "").replaceAll("[()\\d]", "").replaceAll("/.*$", "").toLowerCase().trim()))
//                    .collect(Collectors.toSet());
//            
//            //仅更高要求
//            Set<Word> wordSet3 = words.parallelStream()
//                    .filter(line -> line.contains("▲"))
//                    .map(line -> new Word(line.replaceAll("▲", "").replaceAll("[()\\d]", "").replaceAll("/.*$", "").toLowerCase().trim()))
//                    .collect(Collectors.toSet());
//            
//            //6级以下：较高要求
//            Set<Word> wordSet4 = words.parallelStream()
//                    .filter(line -> !line.trim().contains("▲"))
//                    .map(line -> new Word(line.replaceAll("★", "").replaceAll("[()\\d]", "").replaceAll("/.*$", "").toLowerCase().trim()))
//                    .collect(Collectors.toSet());
//            
//            //更高要求-全部-去重复-全转换为小写
//            Set<Word> wordSet5 = words.parallelStream()
//                    .map(line -> new Word(line.replaceAll("★", "").replaceAll("▲", "").replaceAll("[()\\d]", "").replaceAll("/.*$", "").toLowerCase().trim()))
//                    .collect(Collectors.toSet());
//            
//          //仅重复词汇
//          Set<Word> wordSet6 = words.parallelStream()
//                .filter(line -> RegEx.containsNumber(line))
//                .map(line -> new Word(line.replaceAll("★", "").replaceAll("▲", "").replaceAll("[()\\d]", "").toLowerCase().trim()))
//                .collect(Collectors.toSet());
//            
//          System.out.println("words count1: "+wordSet1.size());
//          System.out.println("words count2: "+wordSet2.size());
//          System.out.println("words count3: "+wordSet3.size());
//          System.out.println("words count4: "+wordSet4.size());
//          System.out.println("words count5: "+wordSet5.size());
//          System.out.println("words count6: "+wordSet6.size());
//          
//            set.addAll(wordSet3);
//        }
//        System.out.println("unique words count: "+set.size());
//        
//        return set;
//    }
    
    //根据词频集合列表，把另一个集合列表合并到词频列表中，没有找到相同单词的剩余集合拼接/排在词频列表最后
    public static Set<Word> mergeWordLevelByFreq(Set<Word> setFreq, Set<Word> setLevel){
          LOGGER.info("setFreq个数："+setFreq.size());
          LOGGER.info("setLevel个数："+setLevel.size());
          
            Iterator<Word> itLevel = setLevel.iterator(); //Level
            while (itLevel.hasNext()) {
                Word wordLevel = (Word) itLevel.next();
                //System.out.println(wordLevel.getWord());
                boolean isFound = false; //是否在setFreq中找到当前单词
                Iterator<Word> itFreq= setFreq.iterator();//Freqency
                while (itFreq.hasNext()) {
                    Word wordFreqency = (Word) itFreq.next();
                    //System.out.println("\t"+wordFreqency.getSpelling());
                    //if (wordFreqency.getSpelling().equalsIgnoreCase(wordLevel.getSpelling())) {
                    if (wordFreqency.getSpelling().equals(wordLevel.getSpelling())) {
                        //System.out.println("\t\twordFreqency==wordLevel");
                        wordFreqency.setLevel(wordLevel.getLevel());
                        isFound = true;
                        break;
                    }
                }
                if (!isFound) {
                    wordLevel.setFrequency("70000"); //使新加进语料库的单词排最后
                    setFreq.add(wordLevel);//把新单词加入单词语料库
                }
            }
            LOGGER.info("结果个数："+setFreq.size());
            return setFreq;
           
          
//          Set<Word> result = setAnc.stream().sorted((a, b) -> b.getFrequency() - a.getFrequency()).collect(Collectors.toSet());
          
//        Collections.sort(names, (String a, String b) -> {
//              return b.compareTo(a);
//          });
          
    //Collections.sort(names, (a, b) -> b.compareTo(a));
          
//          //先转换成list，再排序
//        List<Word> wordList = new ArrayList<Word>(result);
//          Collections.sort(wordList, new Comparator<Word>() {
//              public int compare(Word arg0, Word arg1) {
//                  int freq0 = arg0.getFrequency();
//                  int freq1 = arg1.getFrequency();
//                    if (freq0 < freq1)
//                        return 1;
//                    else if (freq0 > freq1)
//                        return -1;
//                    else 
//                        //return 0;
//                      arg0.getWord().compareTo(arg1.getWord());
//              }
//          });
          
//          // Listing 3
//          Collections.sort(people, new Comparator<Person>() {
//              public int compare(Person lhs, Person rhs) {
//                  if (lhs.getLastName().equals(rhs.getLastName())) {
//                      return lhs.getAge() - rhs.getAge();
//                  } else
//                      return lhs.getLastName().compareTo(rhs.getLastName());
//              }
//          });
                
//        Iterator it = minuend.iterator();//先迭代出来  
//          while(it.hasNext()){//遍历  
//              System.out.println(it.next());  
//          }  
//        LOGGER.info("结果个数："+wordList.size());
//        return wordList;
//          LOGGER.info("结果个数："+result.size());
//          return result;
        }
    
    //
    public static void toSaveCourseLevelWord() {
        String [][]fileLevel = {
            {"小学","/word_primary_school.txt",},//1353
            {"初中","/word_junior_school.txt"},//2171
            {"高中","/word_senior_school.txt",},//2361
            {"大学","/word_university.txt",},//1864
            {"四级","/word_CET4.txt"},
            {"考研","/word_考 研.txt"},
            {"六级","/word_CET6.txt"},
//          {"大学教纲","/大学英语课程教学要求.txt"},//
        };
        Set<Word> set1 = new HashSet<>();
        Set<Word> setLevel = new HashSet<>();
        Set<Word> setResult = new HashSet<>();
        for (int i = 0;i <fileLevel.length;i++) {
            set1 = loadWord(fileLevel[i][0],fileLevel[i][1]);//先获取level比较低的单词集合，后获取levle较高的集合
            set1.removeAll(setLevel); //先把当前集合(set1)中和结果集(setResult)重复的的单词全部除去；
            setLevel.addAll(set1);//再把修剪过的当前集合(set1)加入到结果集(setResult)中
        }
        set1 = loadWordListOfCollegeRequirements2Level();
        set1.removeAll(setLevel); //先把当前集合(set1)中和结果集(setLevel)重复的的单词全部除去；
        setLevel.addAll(set1);//再把修剪过的当前集合(set1)加入到结果集(setLevel)中
        
        set1 = loadWordListOfFreq("/American National Corpus,ANC.txt");
        setResult = mergeWordLevelByFreq(set1,setLevel);
        
        String toSaveFile = "/toSaveCourseLevle2.txt";
        saveWordSet(setResult,toSaveFile);
      }

    public static void saveWordSet(Set<Word> words, String path) {
        //path = Constant.PROJECT_BIN_DIR + path; // 父目录必须存在
        LOGGER.info("开始保存词典：" + path);
        // AtomicInteger ai = new AtomicInteger();
        List<String> lines = words.stream()
                // .sorted()
                .sorted(Word.m_byFreqAndLevel)
                // .sorted((a, b) -> a.getFrequency() - b.getFrequency())
                .map(word -> (word.getFrequency() + "\t" + word.getSpelling() + "\t" + word.getLevel()))
                // .map(word ->
                // (ai.incrementAndGet()+"\t"+word.getWord()+"\t"+word.getLevel()))
                // .map(word -> word.getWord())
                //.distinct() // 删除重复
                .collect(Collectors.toList());
        ResourceUtil.writerFile(path, lines, false);
        //LOGGER.info("保存成功");
    }
    
    public static void saveList(List<Word> worList, String path) {
        // path = "src/target" + path; //父目录必须存在
        LOGGER.info("开始保存词典：" + path);
        // Files.write(Paths.get(path), list);
        List<String> lines = worList.stream().sorted()
                .map(word -> (word.getFrequency() + "\t" + word.getSpelling())).collect(Collectors.toList());
        ResourceUtil.writerFile(path, lines, false);
//        LOGGER.info("保存成功");
    }

    private static void log(String word, String suffix){
        LOGGER.debug("发现复数："+word+"\t"+suffix);
    }
    
    //vowel:元音
    public static boolean isVowel(char _char){
        switch (_char){
            case 'a':return true;
            case 'e':return true;
            case 'i':return true;
            case 'o':return true;
            case 'u':return true;
        }
        return false;
    }

    //stem:(词)干,茎,柄:返回词干的集合(不是词根)
    public static Set<Word> stem(Set<Word> words){
        return words
                .stream()
                .filter(word -> word.getSpelling().length() > 3)
                .filter(word -> !isPlural(words, word))
                .collect(Collectors.toSet());
    }
    
    //返回是复数的集合
    public static Map<String, String> plural(Set<Word> words) {
        Map<String, String> data = new HashMap<>();
        words.stream()
        .filter(word -> word.getSpelling().length() > 3)
        .forEach(word -> {isPlural(words, word, data);});
        return data;
    }
  
    //plural:复数
    public static boolean isPlural(Set<Word> words, Word word){
        return isPlural(words, word, new HashMap<>());
    }
    
    //plural:复数
    public static boolean isPlural(Set<Word> words, Word word, Map<String, String> data){
        String w = word.getSpelling();
        //1、以辅音字母+y结尾,变y为i再加es
        if (w.endsWith("ies")){
            char c = w.charAt(w.length()-4);
            if(!(isVowel(c))
                    && words.contains(new Word(w.substring(0, w.length()-4)+"y"))){
                log(w, "ies");
                data.put(w, "ies");
                return true;
            }
        }
        //2、以ce, se, ze结尾, 加s
        if(w.endsWith("ces")
                || w.endsWith("ses")
                || w.endsWith("zes")){
            if(words.contains(new Word(w.substring(0, w.length()-1)))){
                log(w, "s");
                data.put(w, "s");
                return true;
            }
        }
        //3、以s, sh, ch, x结尾, 加es
        if(w.endsWith("ses")
                || w.endsWith("shes")
                || w.endsWith("ches")
                || w.endsWith("xes")){
            if(words.contains(new Word(w.substring(0, w.length()-2)))){
                log(w, "es");
                data.put(w, "es");
                return true;
            }
        }
        //4、一般情况，加s
        if(w.endsWith("s")){
            if(words.contains(new Word(w.substring(0, w.length()-1)))){
                log(w, "s");
                data.put(w, "s");
                return true;
            }
        }
        return false;
    }
    
    private static String [][]fileLevel = {
            {"小学","/word_primary_school.txt",},//1353
            {"初中","/word_junior_school.txt"},//2171
            {"高中","/word_senior_school.txt",},//2361
            {"大学","/word_university.txt",},//1864
            {"大学教纲","/大学英语课程教学要求.txt"},//
//          {"新概念","/word_new_conception.txt"},
            {"四级","/word_CET4.txt"},
            {"六级","/word_CET6.txt"},
            {"考研","/word_考 研.txt"},
//          {"专四","/word_TEM4.txt"},
//          {"专八","/word_TEM8.txt"},
//          {"BEC","/word_BEC.txt"},
            
//          {"CATTI","/word_CATTI.txt"},
//          {"IELTS","/word_IELTS.txt"},
//          {"TOEFL","/word_TOEFL.txt"},
//          {"SAT","/word_SAT.txt"},
//          {"GRE","/word_GRE.txt"},
//          {"GMAT","/word_GMAT.txt"},
//          {"MBA","/word_MBA.txt"},
//          {"TOEIC","/word_TOEIC.txt"},
        };
        
}
