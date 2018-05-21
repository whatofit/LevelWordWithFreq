package com.myblog.demo;

import java.util.ArrayList;
import java.util.List;

public class UpdateArrayListDemo {

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		System.out.println("未修改前：");
		for (String s:list) {
			System.out.println(s);
		}
		list.set(1, "修改之后的元素");
		System.out.println("已修改后：");
		for (String s:list) {
			System.out.println(s);
		}
	}

}
