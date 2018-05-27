package com.myblog.filter;

import java.io.File;
import java.io.FilenameFilter;

//filename Extension Filter
//MS Office文档（doc、docx、ppt、pptx、xls、xlsx）、pdf、epub、html、rtf、txt、csv等格式。
public class FileExtNameFilter implements FilenameFilter {
	private String[] type; // .txt/.pdf/.doc/.docx/.xlsx/.xls/...

	public FileExtNameFilter(String type) {
		this.type = new String[1];
		this.type[0] = type;
	}

	public FileExtNameFilter(String[] type) {
		this.type = type;
	}

	public boolean accept(File dir, String name) {
		for (String type : this.type) {
			return name.endsWith(type);
		}
		return false;
	}
}