package com.level.demo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

//System.out.print重定向到文件实例

public class RedirectSystemOut {

	public static void main(String[] args) {
		new RedirectSystemOut().redirectOut();
	}

	public void redirectOut() {
		try {
			System.setOut(new PrintStream(new FileOutputStream("D:/systemOut.txt")));
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			return;
		}
		System.out.println("Hello World!");
		Arrays.asList("b", "a", "c", "d").forEach(System.out :: println); 
	}
}
