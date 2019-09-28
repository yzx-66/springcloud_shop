package com.yzx.shop.upload.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {

    String uploadImage(MultipartFile file) throws IOException;
}
