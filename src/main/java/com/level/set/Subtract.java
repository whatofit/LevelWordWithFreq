package com.level.set;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.level.Constant;
import com.level.util.CfgUtil;
import com.level.util.ResourceUtil;

public class Subtract {

//    private static final Logger LOGGER = LoggerFactory.getLogger(Subtract.class);

    public Subtract() {
    }

    public static void main(String[] args) {
        doSubtract();
    }

    /**
     * #求差集:集合A减去集合B，最后(剔重并)排序
     */
    public static void doSubtract() {
        long startTime = System.currentTimeMillis();
        // 1.读取文件路径
        String cfg_minuend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_minuend");
        String cfg_subtrahend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_subtrahend");
        String cfg_subtract_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_subtract_result");

        // 2.加载集合
        List<String> minuend_Words = WordFilesMgr.loadWordsFromFile(cfg_minuend.split(","));
        List<String> subtrahend_Words = WordFilesMgr.loadWordsFromFile(cfg_subtrahend.split(","));
        List<String> resultWords = setDifference(minuend_Words, subtrahend_Words);
        // 3.实现排序
        Collections.sort(resultWords, new WordComparator());

        // 4.保存结果集合C到文件
        ResourceUtil.writerFile(Constant.PATH_RESOURCES + cfg_subtract_result, resultWords, false);

        long endTime = System.currentTimeMillis();
        System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
    }

    /**
     * 求差集
     * 
     * @param first
     * @param second
     * @return
     */
    public static List<String> setDifference(List<String> first, List<String> second) {
    	System.out.println("求差集词典1：" + first.size());
    	System.out.println("求差集词典2：" + second.size());

        List<String> result = new ArrayList<String>();
        result.clear();
        result.addAll(first);
        result.removeAll(second);
        // Collections.sort(result, new WordComparator());
        //System.out.println("差集：" + result);

        System.out.println("差集词典：" + result.size());
        return result;
    }

    // 自定义比较器：按书的价格排序
    static class WordComparator implements Comparator<Object> {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
            String str1 = (String) object1;
            String str2 = (String) object2;
            return str1.compareToIgnoreCase(str2);
        }
    }
}
