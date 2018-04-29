package com.myblog.txt;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import com.myblog.Constant;

//https://blog.csdn.net/xixiruyiruyi/article/details/53466279
//注意：生成html文件的需要需要注意meta头部的charset的配置。要根据所在项目的编码格式来定，
//具体可以参考我的另一篇博文： java生成动态html文档为乱码的问题解决
//https://blog.csdn.net/xixiruyiruyi/article/details/53389722
public class GenerateHtml {

	public static void main(String[] args) {
		String fileName = Constant.PATH_RESOURCES +"/test.html";
		String htmlTitle = "html write test";
		String htmlBody = "html body test";
		//<font color=\"#FF0000\">%s</font>
		String bgColor = "#191970";
		String fgColor = "#FF0000";
		String bgColor2 = "#DC143C";
		String fgColor2 = "#FFFFFF";
		String fontFmt = "<span style=\"background-color:%s;color:%s;\">%s</span>";
		htmlBody = String.format(fontFmt, bgColor, fgColor,htmlBody);
		htmlBody += String.format(fontFmt, bgColor2, fgColor2,"word");
		WriteHtmlWithBody(fileName,htmlBody, htmlTitle);
	}
	
	public static void WriteHtmlWithBody(String fileName, String htmlBody,String htmlTitle) {
		// 用于存储html字符串
		StringBuilder stringHtml = new StringBuilder();
		// 输入HTML文件内容
		stringHtml.append("<html><head>");
//		stringHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GBK\">");
		stringHtml.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>");
//		stringHtml.append("<style type=\"text/css\">");
		stringHtml.append("</style>");
		stringHtml.append(String.format("<title>%s</title>", htmlTitle));
		stringHtml.append("</head>");
		stringHtml.append("<body>");
//		stringHtml.append("<body bgcolor=\"#FFF8DC\">");
		stringHtml.append("<div align=\"left\">");
//		stringHtml.append("<div>hello</div>");
		stringHtml.append(htmlBody);
		stringHtml.append("</div>");
		stringHtml.append("</body></html>");
		try {
			// 打开文件
			PrintStream printStream = new PrintStream(new FileOutputStream(fileName));
			// 将HTML文件内容写入文件中
			printStream.println(stringHtml.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
