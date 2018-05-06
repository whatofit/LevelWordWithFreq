package com.myblog.rtf2txt;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.myblog.Constant;
import com.myblog.util.RegEx;
import com.myblog.util.ResourceUtil;
import com.myblog.util.Utils;

public class ParseTheEconomist2Txt {

	public static void main(String[] args) {
		doPdf2Txt();
		//doPdf2Txt();//doParseTheEconomist();
		System.out.println("done!=");
	}

	public static void doPdf2Txt() {
		String forderPath = "E:/FanMingyou/The Economist";
		List<String> pdfFileList = new ArrayList<String>();
		pdfFileList = Utils.traverseFile(new File(forderPath), pdfFileList, ".pdf");
		//System.out.println("--resultFileName:" + resultNameList);
		toText(pdfFileList);
		for (String filename : pdfFileList) {
			System.out.println(filename);
		}
	}
	
    /**
     * @param args
     */
    public static void toText(List<String> pdfFileList) {
        for (String pdfFilename:pdfFileList) {
            // 1.读取pdf文件路径
            //String cfg_pdf = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_pdf");
            //String pdfFilename = Constant.PATH_RESOURCES + cfg_pdf;
            String text = RtfUtil.readPdf2Txt(pdfFilename);
            // 2.写入对应的text文件
            String outTextFilename = pdfFilename.replaceAll(".pdf$", ".txt"); //匹配到行尾
            // 生成文本文件
            ResourceUtil.writerFile(outTextFilename, text, false);
            System.out.println("done!outTextFilename="+outTextFilename);
    	}
    }
    
}
