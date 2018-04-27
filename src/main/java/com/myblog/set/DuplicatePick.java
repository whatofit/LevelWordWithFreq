package com.myblog.set;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.myblog.Constant;
import com.myblog.util.CfgUtil;
import com.myblog.util.ResourceUtil;

public class DuplicatePick {

    public DuplicatePick() {
    }

    public static void main(String[] args) {
        doDuplicatePick();
    }

    /**
     * #求文件重复单词集合，删除只出现一次的单词，保留出现两次及两次以上的单词，最后并剔重,排序
     */
    public static void doDuplicatePick() {
        long startTime = System.currentTimeMillis();
        // 1.读取单词集合路径
        String cfg_duplicate_pick = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_duplicate_pick");
        // 2.加载集合
        List<String> wordLines = WordFilesMgr.loadWordsFromFile(cfg_duplicate_pick.split(","));

     // #剔重/删除重复单词,保留唯一单词并排序
//      for (int i = list1.size() - 1; i>=0; i--) {
//      for(int j = i-1; j >= 0;j--) {
//          if (list1.get(j) == list1.get(i)) {
//              list3.add(list1.get(i));
//              break;
//          }
//      }
//  }
        
        //循环获取重复的单词
        List<String> duplicateLines = new ArrayList<String>();
        for (int i = 0; i < wordLines.size() - 1; i++) {
            for (int j = i + 1; j < wordLines.size() - 1; j++) {
                if (wordLines.get(j).equals(wordLines.get(i))) {
                    duplicateLines.add(wordLines.get(i));
                    break;
                }
            }
        }
        // #剔重/删除重复单词,保留唯一单词并排序
        List<String> uniqueWords = duplicateLines.parallelStream().distinct().sorted().collect(Collectors.toList());

        //Java8 新特性之集合removeIf
        //Predicate<String> predicate = (s) -> s.startsWith("#"); // 这里单独定义了过滤器
        //wordLines.removeIf(predicate);
        //wordLines.retainAll(uniqueWords);
        
        //List<String> resultWords = uniqueWords.parallelStream().distinct().sorted().collect(Collectors.toList());
        System.out.println("resultWords,uniqueWords:" + uniqueWords.size());
        // 实现排序
        // Collections.sort(resultWords, new WordComparator());
        // 4.保存结果集合C到文件
        String uplicate_pick_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_duplicate_pick_result");
        ResourceUtil.writerFile(Constant.PATH_RESOURCES + uplicate_pick_result, uniqueWords, false);

        long endTime = System.currentTimeMillis();
        System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
    }
}
