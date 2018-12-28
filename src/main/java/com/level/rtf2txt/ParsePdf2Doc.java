package com.level.rtf2txt;

import com.level.Constant;
import com.level.util.CfgUtil;

public class ParsePdf2Doc {

	public ParsePdf2Doc() {
	}

	public static void main(String[] args) {
		// 1.读取pdf文件路径
		String cfg_pdf = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_english_pdf");
		String pdfFilename = Constant.PATH_RESOURCES + cfg_pdf;
		RtfUtil.readPdf2Doc(pdfFilename);
		// 2.写入对应的docx文件
		System.out.println("done!=");
	}
}
