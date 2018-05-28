package com.myblog.rtf2txt;

import com.myblog.Constant;
import com.myblog.util.CfgUtil;

public class ParsePdf2Txt {
	public ParsePdf2Txt() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 1.读取pdf文件路径
		String cfg_pdf = CfgUtil.getPropCfg(Constant.FILE_CONFIG_FILE, "cfg_english_pdf");
		String pdfFilename = Constant.PATH_RESOURCES + cfg_pdf;
		RtfUtil.pdf2Text(pdfFilename);
	}
}