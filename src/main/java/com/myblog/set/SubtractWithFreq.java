package com.myblog.set;

import java.util.Comparator;
import java.util.List;

import com.myblog.Constant;
import com.myblog.util.CfgUtil;
import com.myblog.util.ResourceUtil;

public class SubtractWithFreq {

    public SubtractWithFreq() {
    }

    public static void main(String[] args) {
        doSubtractWithFreq();
    }

    /**
     * #求差集:集合A减去集合B，最后(剔重并)排序
     */
    public static void doSubtractWithFreq() {
        long startTime = System.currentTimeMillis();
        // 1.读取文件路径
        String cfg_minuend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_minuend_with_freq");
        String cfg_subtrahend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_subtrahend_with_freq");
        //String cfg_subtract_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_subtract_with_freq_result");

        // 2.加载集合
        //List<String> minuend_Words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + cfg_minuend);
        //List<String> subtrahend_Words = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + cfg_subtrahend);
        List<String> minuend_Words = WordFilesMgr.loadWordsFromFile2(cfg_minuend.split(","));
        List<String> subtrahend_Words = WordFilesMgr.loadWordsFromFile2(cfg_subtrahend.split(","));
        setDifferenceWithFreq(minuend_Words, subtrahend_Words);
        // 3.实现排序
        //cfg_minuend.Collections.sort(resultWords, new WordComparatorWithFreq());

        // 4.保存结果集合C到文件
        //ResourceUtil.writerFile(Constant.PATH_RESOURCES + cfg_subtract_result, resultWords, false);

        long endTime = System.currentTimeMillis();
        System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
    }

    /**
     * 求差集
     * 
     * @param first
     * @param second
     * @return 
     * @return
     */
    public static  void setDifferenceWithFreq(List<String> first, List<String> second) {
        System.out.println("求差集词典1: " + first.size());
        System.out.println("求差集词典2: " + second.size());
//        List<String> resultFirst = new ArrayList<String>();
//        resultFirst.clear();
//        resultFirst.addAll(first);
//        resultFirst.removeAll(second);
//        // Collections.sort(result, new WordComparator());
//        //System.out.println("差集：" + result);
//        System.out.println("差集词典first-second：" +  resultFirst.size());
//        String cfg_subtract_with_freq_minuend_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_subtract_with_freq_minuend_result");
//        ResourceUtil.writerFile(Constant.PATH_RESOURCES + cfg_subtract_with_freq_minuend_result, resultFirst, false);
//        
//        List<String> resultSecond = new ArrayList<String>();
//        resultSecond.clear();
//        resultSecond.addAll(second);
//        resultSecond.removeAll(first);
//        String cfg_subtract_with_freq_subtrahend_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_subtract_with_freq_subtrahend_result");
// 		ResourceUtil.writerFile(Constant.PATH_RESOURCES + cfg_subtract_with_freq_subtrahend_result, resultSecond, false);
//        System.out.println("差集词典second-first：" +  resultSecond.size());


      //List<String> firstResult = new ArrayList<String>();
//		List<String> secondResult = new ArrayList<String>();
		int firstMismatchingCnt = 0;
		int i = firstMismatchingCnt;
		while (first.size() > firstMismatchingCnt) {
			String firstLine = first.get(i);
			String[] arrFirst = firstLine.split("\\s");
			boolean isFound = false;
			int j = 0;
			while (second.size() > j) {
				String secondLine = second.get(j);
				String[] arrSecond = secondLine.split("\\s");
				if (arrFirst[1].equalsIgnoreCase(arrSecond[1])) {
					isFound = true;
					first.remove(i);
					second.remove(j);
					break;// 终止循环
				}
				j++;
			}
			if (!isFound) {
				firstMismatchingCnt++;
				i = firstMismatchingCnt;
			}
      }
      System.out.println("差集词典first-second：" +  first.size());
      String cfg_subtract_with_freq_minuend_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_subtract_with_freq_minuend_result");
      ResourceUtil.writerFile(Constant.PATH_RESOURCES + cfg_subtract_with_freq_minuend_result, first, false);
      
      String cfg_subtract_with_freq_subtrahend_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_subtract_with_freq_subtrahend_result");
      ResourceUtil.writerFile(Constant.PATH_RESOURCES + cfg_subtract_with_freq_subtrahend_result, second, false);
      System.out.println("差集词典second-first：" +  second.size());
        return ;
    }

    // 自定义比较器：按书的价格排序
    static class WordComparatorWithFreq implements Comparator<Object> {
        public int compare(Object object1, Object object2) {// 实现接口中的方法
            String str1 = (String) object1;
            String str2 = (String) object2;
            return str1.compareToIgnoreCase(str2);
        }
    }
}
