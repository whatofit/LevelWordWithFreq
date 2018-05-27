package com.myblog.rtf2txt;

import java.util.List;

import com.myblog.util.Utils;

public class ParseTheEconomist2Txt {

	public static void main(String[] args) {
		String forderPath = "E:/FanMingyou/The Economist";
		doPdf2Txt(forderPath);
		System.out.println("done!=");
	}

	public static void doPdf2Txt(String forderPath) {
		List<String> pdfFileList = Utils.traverseFile(forderPath, ".pdf");
		toText(pdfFileList);
	}

	/**
	 *
	 */
	public static void toText(List<String> pdfFileList) {
		for (String pdfFilename : pdfFileList) {
			RtfUtil.pdf2Text(pdfFilename);
		}
	}
}
