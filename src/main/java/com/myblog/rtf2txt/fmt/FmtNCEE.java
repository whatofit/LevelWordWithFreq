package com.myblog.rtf2txt.fmt;

import java.util.List;
import java.util.stream.Collectors;

import com.myblog.Constant;
import com.myblog.util.CfgUtil;
import com.myblog.util.RegEx;
import com.myblog.util.ResourceUtil;

//格式化高考词汇表
public class FmtNCEE {

    public static void main(String[] args) {
        doFmtNCEE();
    }

    public static void doFmtNCEE() {
        long startTime = System.currentTimeMillis();
        // 1.读取单词集合路径
        String cfg_english_path = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_english_txt");
        // String forderPath = "E:/FanMingyou/The Economist";
        String englishTxtFile = Constant.PATH_RESOURCES + cfg_english_path;
        // 2.加载/读取原文件line集合
        List<String> fileLines = ResourceUtil.readFileLines(englishTxtFile);
        // 3.处理文件集合:格式化/替换
        List<String> wordLines = fmtNCEEWord(fileLines);
        // 3.保存word list
        String cfg_english_txt_result_path = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_english_txt_result");
        String txtWordFile = Constant.PATH_RESOURCES + cfg_english_txt_result_path;
        ResourceUtil.writerFile(txtWordFile, wordLines, false);
        long endTime = System.currentTimeMillis();
        System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
    }

    public static List<String> fmtNCEEWord(List<String> fileLines) {
        List<String> wordList = fileLines.parallelStream()
                .filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
                .map(RegEx::fmtNCEEWordLine)
                .distinct() // 剔重
                .collect(Collectors.toList());
        // .collect(Collectors.toSet());
        // .collect(Collectors.toMap(Word::getSpelling, Word::getSentences));
        // System.out.println("unique words count: " + wordMap.size());
        return wordList;
    }
}
