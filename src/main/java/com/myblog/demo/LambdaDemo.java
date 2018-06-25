package com.myblog.demo;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@FunctionalInterface
interface ICreater<T extends List<?>> {
	T create();
}

public class LambdaDemo {
	public static void main(String[] args) {
		Arrays.asList("b", "a", "c", "d").forEach(System.out :: println);
//		forEach();
	}

	private static void forEach() {

		LambdaDemo lambdaDemo = new LambdaDemo();

		String[] strings = { "acb", "abc", "cb", "bc" };
		List<String> list_2 = lambdaDemo.asList(LinkedList::new, strings);
		Collections.sort(list_2, String::compareTo);
		list_2.stream().forEach(System.out::println);
	}

	public <T> List<T> asList(ICreater<List<T>> creater, T... t) {
		List<T> list = creater.create();
		for (T a : t)
			list.add(a);
		return list;
	}
}

// 作者：英勇青铜5
// 链接：https://www.jianshu.com/p/0b4b59966276
// 來源：简书
// 简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。