package com.weicoder.test;

import com.weicoder.common.io.FileUtil; 
import com.weicoder.http.HttpUpload;

public class Upload {

	public static void main(String[] args) {
		String url = "http://upload.i4322.com/upload/upload";
		String file = "e:\\1.jpg";
		System.out.println(HttpUpload.upload(url, FileUtil.newFile(file)));
	} 
}
