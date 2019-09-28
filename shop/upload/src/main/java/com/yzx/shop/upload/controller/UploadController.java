package com.yzx.shop.upload.controller;

import com.yzx.shop.upload.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("image")
    public ResponseEntity<String> uploadImage(MultipartFile file) throws IOException {
        String imagePath=uploadService.uploadImage(file);
        if(StringUtils.isNotBlank(imagePath)){
            return ResponseEntity.ok(imagePath);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }
}
