package com.weicoder.test;

import com.weicoder.common.io.FileUtil; 
import com.weicoder.httpclient5.HttpUpload;

public class UploadTest {

	public static void main(String[] args) {
		String url = "http://127.0.0.1:8080/upload/upload?type=qcloud";
		String file = "e:\\1.txt";
		System.out.println(HttpUpload.upload(url, FileUtil.newFile(file)));
	} 
}
