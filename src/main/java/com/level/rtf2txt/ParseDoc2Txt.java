package com.level.rtf2txt;

import com.level.Constant;

public class ParseDoc2Txt {

	public ParseDoc2Txt() {
	}

	public static void main(String[] args) {
		//E:\FanMingyou\workspace\workspace-sts-3.9.2.RELEASE_2017-12-27\LevelWordWithFreq\src\main\resources\the old exam\英语一\1980-2015年考研英语(一)历年真题及答案解析.doc
		String filePath = Constant.PATH_RESOURCES + "/the old exam/英语一/1980-2015年考研英语(一)历年真题及答案解析.doc";
		RtfUtil.doc2Text(filePath);
	}

}
