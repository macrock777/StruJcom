package com.efive.agencyonline.common;

import java.io.File;
import java.io.FilenameFilter;

public class CustomFDirFilter implements FilenameFilter {

	String pattern,fname;

	public CustomFDirFilter(String fname,String pattern) {
		this.pattern = pattern.toLowerCase();
		this.fname = fname.toLowerCase();
	}

	@Override
	public boolean accept(File dir, String filename) {

		filename = filename.toLowerCase();
		if (filename.startsWith(fname) && filename.endsWith(pattern)) {
			return true;
		}
		return false;
	}

}
