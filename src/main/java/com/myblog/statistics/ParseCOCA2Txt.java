package com.myblog.statistics;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.myblog.Constant;
import com.myblog.model.Word;
import com.myblog.set.WordFilesMgr;
import com.myblog.util.CfgUtil;
import com.myblog.util.RegEx;
import com.myblog.util.ResourceUtil;

public class ParseCOCA2Txt {

    public ParseCOCA2Txt() {
    }

    /**
     * 解析COCA的text文件;
     */
    public static void doParseCOCA() {
        long startTime = System.currentTimeMillis();
        String cocaFile = Constant.PATH_RESOURCES + "/美国当代英语语料库COCA词频20000.txt";
        // 1.读取加载coca文件
        List<String> cocaFileLines = ResourceUtil.readFileLines(cocaFile);
        // 2.处理coca原文件集合为word list
        List<String> curWords = cocaFileLines.parallelStream()
                .filter(line -> !line.trim().startsWith("#") && !"".equals(line.trim()))
                // .filter(line -> !line.contains("-"))
                .map(RegEx::catchWordLine)
                .filter(line -> !"".equals(line.trim()))
                //.filter(line -> !line.contains("n''t"))// //29    n''t    x   //4103 o''clock r  
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        
        Collections.sort(curWords, new WordLineComparator());
        // 3.保存word list
        String cocaWordFile = Constant.PATH_RESOURCES + "/美国当代英语语料库COCA词频20000_words.txt";
        ResourceUtil.writerFile(cocaWordFile, curWords, false);
        long endTime = System.currentTimeMillis();
        System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
    }

    // 自定义比较器：按单词的频率排序
    private static class WordLineComparator implements Comparator<String>   {

        @Override
        public int compare(String o1, String o2) { // 实现接口中的方法
            String []freq1 = o1.trim().split("\\s");
            String []freq2 = o2.trim().split("\\s");
            int nFreq1 = Integer.parseInt(freq1[0]);
            int nFreq2 = Integer.parseInt(freq2[0]);
            if (nFreq1 > nFreq2)  
            {  
                return 1;  
            }  
            else if (nFreq1 < nFreq2)  
            {  
                return -1;  
            }  
            else  
            {  
                return 0;  
            }  
        }
    }
    
    public static void main(String[] args) {
        doParseCOCA();
    }

}
