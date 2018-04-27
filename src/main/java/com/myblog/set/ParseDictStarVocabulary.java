package com.myblog.set;

import com.myblog.Constant;
import com.myblog.util.FileUtil;
import com.myblog.util.ParseHtmlUtil;

public class ParseDictStarVocabulary {
    static int[][] vocabulary_dict_star = { 
            { 5, 5, },  // 海词5星基本词汇，属常用1000词。   共5组, 1236个词
            { 4, 7 },   // 海词4星核心词汇，属常用3000词。   共7组, 2011个词
            { 3, 10, }, // 海词3星常用词汇，属常用6000词。   共10组,2986个词
            { 2, 14, }, // 海词2星扩展词汇，属常用10000词。共14组,3955个词
            { 1, 32 },  // 海词1星畅通词汇，属常用20000词。共32组,9335个词
    };

    public ParseDictStarVocabulary() {
    }

    public static void main(String[] args) {
        String wordFile = "";// D:/base5-0.html
        for (int i = 0; i < vocabulary_dict_star.length; i++) {
            for (int j = 0; j < vocabulary_dict_star[i][1]; j++) {
                wordFile = String.format("/base%s-%s.html", vocabulary_dict_star[i][0], j);
                // System.out.println("wordFile:" + wordFile);
                ParseHtmlUtil.parseDictHubMain(FileUtil.readFile3(Constant.PATH_DICT_STAR + wordFile));
            }
        }
    }

}
