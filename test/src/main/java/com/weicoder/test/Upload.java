package com.weicoder.test;

import com.weicoder.common.io.FileUtil; 
import com.weicoder.http.HttpUpload;

public class Upload {

	public static void main(String[] args) {
		String url = "http://127.0.0.1:8080/upload/upload?type=qcloud";
		String file = "e:\\1.jpg";
		System.out.println(HttpUpload.upload(url, FileUtil.newFile(file)));
	} 
}