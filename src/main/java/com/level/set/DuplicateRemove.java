package com.level.set;

import java.util.List;
import java.util.stream.Collectors;

import com.level.Constant;
import com.level.util.CfgUtil;
import com.level.util.ResourceUtil;

public class DuplicateRemove {

    public DuplicateRemove() {
    }

    public static void main(String[] args) {
        doDuplicateRemove();
    }

    /**
     * #无重复并集;
     * list2.removeAll(list1);
     * list1.addAll(list2);
     * 
     * #剔重操作：删除重复单词,保留唯一单词
     */
    public static void doDuplicateRemove() {
        long startTime = System.currentTimeMillis();
        // 1.读取单词集合路径
        String cfg_duplicate_remove = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_duplicate_remove");
        // 2.加载集合
        List<String> wordLines = WordFilesMgr.loadWordsFromFile(cfg_duplicate_remove.split(","));

        // #剔重/删除重复单词,保留唯一单词并排序
        List<String> resultWords = wordLines.parallelStream().distinct().sorted().collect(Collectors.toList());
        // 实现排序
        // Collections.sort(resultWords, new WordComparator());
        // 4.保存结果集合C到文件
        String cfg_duplicate_remove_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_duplicate_remove_result");
        ResourceUtil.writerFile(Constant.PATH_RESOURCES + cfg_duplicate_remove_result, resultWords, false);

        long endTime = System.currentTimeMillis();
        System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
    }

}
