package com.myblog.filter;

import java.io.File;
import java.io.FilenameFilter;

//filename Extension Filter
//MS Office文档（doc、docx、ppt、pptx、xls、xlsx）、pdf、epub、html、rtf、txt、csv等格式。
public class FileExtNameFilter implements FilenameFilter {
	private String[] fileTypes; // .txt/.pdf/.doc/.docx/.xlsx/.xls/...

	public FileExtNameFilter(String fileType) {
		this.fileTypes = new String[1];
		this.fileTypes[0] = fileType;
	}

	public FileExtNameFilter(String[] fileTypes) {
		this.fileTypes = fileTypes;
	}

	public boolean accept(File dir, String name) {
		for (String type : this.fileTypes) {
			return name.endsWith(type);
		}
		return false;
	}
}