package com.myblog.rtf2txt.fmt;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;

import com.myblog.Constant;
import com.myblog.set.WordFilesMgr;
import com.myblog.util.CfgUtil;
import com.myblog.util.ResourceUtil;

class MyOperator<T> implements UnaryOperator<T> {
	T varc1;

	public T apply(T varc) {
		return varc1;
	}
}

public class FmtDuplicateWord {

	public static void main(String[] args) {
		doFmtDuplicateWord();
		// ListReplaceAllDemo();
	}

	public static void doFmtDuplicateWord() {
		long startTime = System.currentTimeMillis();
		// 1.读取文件路径/#被加数augend/summand:#加数addend
        String cfg_minuend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_minuend");
        String cfg_subtrahend = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_subtrahend");
        String cfg_subtract_result = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_subtract_result");

        // 2.加载集合
        List<String> minuend_Words = WordFilesMgr.loadWordsFromFile(cfg_minuend.split(","));
        List<String> subtrahend_Words = WordFilesMgr.loadWordsFromFile(cfg_subtrahend.split(","));
		// 2.格式化/替换
		for (int i = 0; i < subtrahend_Words.size(); i++) {
			Collections.replaceAll(minuend_Words, subtrahend_Words.get(i), "#" + subtrahend_Words.get(i));
		}
		// 3.保存word list
		String cocaWordFile = Constant.PATH_RESOURCES + cfg_subtract_result;
		ResourceUtil.writerFile(cocaWordFile, minuend_Words, false);
		//System.out.println("List :" + minuend_Words);
		long endTime = System.currentTimeMillis();
		System.out.println("执行耗时 : " + (endTime - startTime) / 1000f + " 秒 ");
	}

	public static void ListReplaceAllDemo() {
		List<String> list = Arrays.asList("one Two three Four five six one three Four".split(" "));
		System.out.println("List :" + list);
		// Collections.replaceAll(list, "one", "hundrea");
		list.replaceAll(v -> v.toUpperCase());

		// list.replaceAll(new UnaryOperator<String>(){
		// @Override
		// public String apply(String str){
		// if(str.length()>3)
		// return str.toUpperCase();
		// return str;
		// }
		// });

		// list.replaceAll(str -> {
		// if(str.length() >3)
		// return str.toUpperCase();
		// return str;
		// });

		System.out.println("replaceAll: " + list);
	}

	// public static void ListReplaceAllDemo2() {
	//
	// ArrayList<String> color_list;
	// MyOperator<String> operator;
	//
	// color_list = new ArrayList<>();
	// operator = new MyOperator<>();
	//
	// operator.varc1 = "White";
	//
	// // use add() method to add values in the list
	// color_list.add("White");
	// color_list.add("Black");
	// color_list.add("Red");
	// color_list.add("White");
	// color_list.add("Yellow");
	// color_list.add("White");
	//
	// System.out.println("List of Colors");
	// System.out.println(color_list);
	//
	// // Replace all colors with White color
	// color_list.replaceAll(operator);
	// System.out.println("Color list, after replacing all colors with White color
	// :");
	// System.out.println(color_list);
	//
	// ArrayList<String> list = new ArrayList<>(Arrays.asList("I", "love", "you",
	// "too"));
	// list.replaceAll(new UnaryOperator<String>() {
	// @Override
	// public String apply(String str) {
	// if (str.length() > 3)
	// return str.toUpperCase();
	// return str;
	// }
	// });
	//
	// ArrayList<String> list2 = new ArrayList<>(Arrays.asList("I", "love", "you",
	// "too"));
	// list2.replaceAll(str -> {
	// if (str.length() > 3)
	// return str.toUpperCase();
	// return str;
	// });
	//
	// HashMap<Integer, String> map = new HashMap<>();
	// map.put(1, "one");
	// map.put(2, "two");
	// map.put(3, "three");
	// map.replaceAll(new BiFunction<Integer, String, String>() {
	// @Override
	// public String apply(Integer k, String v) {
	// return v.toUpperCase();
	// }
	// });
	//
	// HashMap<Integer, String> map2 = new HashMap<>();
	// map2.put(1, "one");
	// map2.put(2, "two");
	// map2.put(3, "three");
	// map2.replaceAll((k, v) -> v.toUpperCase());
	// }
}
