package com.myblog.set;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.myblog.Constant;
import com.myblog.util.CfgUtil;
import com.myblog.util.ResourceUtil;

public class VocabularyLowercase {

    public VocabularyLowercase() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        // 1.读取单词集合路径
        String cfg_vocabulary_to_lowercase = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_vocabulary_to_lowercase");
        // 2.加载集合
        List<String> resultWords = new ArrayList<String>();
        List<String> wordFileLines = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + cfg_vocabulary_to_lowercase);
        for(String line : wordFileLines){
            //System.out.println("parse word file: "+line);
            resultWords.add(line.toLowerCase()); // 小写
        }
        System.out.println("resultWords,resultWords:" + resultWords.size());
        // 实现排序
        // Collections.sort(resultWords, new WordComparator());
        // 4.保存结果集合C到文件
        String cfg_vocabulary_to_lowercase_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_vocabulary_to_lowercase_result");
        ResourceUtil.writerFile(Constant.PATH_RESOURCES + cfg_vocabulary_to_lowercase_result, resultWords, false);

        long endTime = System.currentTimeMillis();
        System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
    }

}
