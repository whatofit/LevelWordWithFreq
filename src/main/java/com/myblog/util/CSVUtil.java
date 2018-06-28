package com.myblog.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

public class CSVUtil {
	//CSV文件分隔符
	private final static char NEW_LINE_SEPARATOR = '\n';
	
	public static CSVParser getCSVParser(String filePath) throws IOException
	{
		CSVFormat format = CSVFormat.DEFAULT.withHeader();
		InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
		return new CSVParser(isr, format);
	}
 
	public static CSVPrinter getCSVPrinter(String filePath) throws IOException
	{
		//初始化csvformat
		CSVFormat format = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);
		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
		//创建FileWriter/CSVPrinter对象
		return new CSVPrinter(osw, format);
	}

	
	public static void main(String[] args) {

	}

}
