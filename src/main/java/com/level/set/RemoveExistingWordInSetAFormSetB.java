package com.level.set;

import java.util.List;

import com.level.Constant;
import com.level.util.ResourceUtil;

public class RemoveExistingWordInSetAFormSetB {

	public static void main(String[] args) {
		doRemoveExistingWord();
	}

	/**
	 * #从List A中删除List B中的所有单词 从ListA的后面开始删除
	 */
	public static void doRemoveExistingWord() {
		long startTime = System.currentTimeMillis();
		// 1.读取文件路径
		String cfg_minuend = "/American National Corpus,ANC.txt";
		String cfg_subtrahend = "/cfg_duplicate_pick_result.txt";
		String cfg_subtract_result = "/cfg_subtract_with_freq_result_xxxx.txt";

		// 2.加载集合
		List<String> first = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + cfg_minuend);
		List<String> second = ResourceUtil.readFileLines(Constant.PATH_RESOURCES + cfg_subtrahend);

		for (int i = 0; i < second.size(); i++) {
			boolean isFound = false;
			String secondLine = second.get(i);
			String[] arrSecond = secondLine.split("\\s");
			for (int j = 0; j < first.size(); j++) {
				String firstLine = first.get(j);
				String[] arrFirst = firstLine.split("\\s");
				if (arrFirst[1].equalsIgnoreCase(arrSecond[0])) {
					if (!isFound) {
						first.set(j, arrFirst[0] + "\t" + arrSecond[0]);
						isFound = true;
						// break;
					} else {
						first.remove(j);
						// j--;
						System.out.println("firstLine : " + firstLine);
					}
				}
			}
		}

		// 3.实现排序
		// cfg_minuend.Collections.sort(resultWords, new WordComparatorWithFreq());

		// 4.保存结果集合C到文件
		ResourceUtil.writerFile(Constant.PATH_RESOURCES + cfg_subtract_result, first, false);

		long endTime = System.currentTimeMillis();
		System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
	}
}
