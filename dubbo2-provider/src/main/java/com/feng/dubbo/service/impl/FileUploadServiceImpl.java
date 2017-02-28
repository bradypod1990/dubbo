package com.feng.dubbo.service.impl;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;

import com.feng.dubbo.service.FileUploadService;

public class FileUploadServiceImpl implements FileUploadService, Serializable{

	@Override
	public String upload(String path, InputStream in) {
		try {
			FileOutputStream out = new FileOutputStream(path);
			int n = -1;
			byte[] b = new byte[10240];
			while((n=in.read(b)) != -1){
				System.out.println(new String(b,0,n,"utf-8"));
				out.write(b, 0, n);
			}
			out.close();
			out.flush();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "upload complete !";
	}

}
