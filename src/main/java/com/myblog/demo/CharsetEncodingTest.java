package com.myblog.demo;

import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public class CharsetEncodingTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println("file.encoding:" + System.getProperty("file.encoding"));
		System.out.println("sun.jnu.encoding:" + System.getProperty("sun.jnu.encoding"));
//		displayAllProp();
		encodingTest();
	}
	
	public static void displayAllProp() {
		Properties pro = System.getProperties();
		Set<Entry<Object, Object>> entrySet = pro.entrySet();
		for (Entry<Object, Object> entry : entrySet) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
	
	public static void encodingTest() throws UnsupportedEncodingException {
		String s = "我们是中国人";
		//如果使用不带参数的getBytes(),会使用平台系统默认的字符集,有时会有乱码的
		//bytes using the platform's default charset,
		byte[] bytes = s.getBytes("utf-8");
//		byte[] bytes = s.getBytes();//
		String s2 = new String(bytes);
		System.out.println(s2);
	}

}
