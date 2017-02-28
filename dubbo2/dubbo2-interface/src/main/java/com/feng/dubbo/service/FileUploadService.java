package com.feng.dubbo.service;

import java.io.InputStream;

public interface FileUploadService {

	String upload(String path, InputStream in);
}
