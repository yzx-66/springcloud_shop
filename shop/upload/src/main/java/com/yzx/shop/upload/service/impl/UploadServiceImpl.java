package com.yzx.shop.upload.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.domain.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.yzx.shop.upload.service.UploadService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FastFileStorageClient fileStorageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Value("fdfs.tracker-list")
    private String virtualHost;

    private final String[] SUFFIX={"jpg","gif","png"};

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String suffix=StringUtils.split(file.getOriginalFilename(),".")[1];
        if(!ArrayUtils.contains(SUFFIX,suffix)){
            return null;
        }

        BufferedImage bufferedImage= ImageIO.read(file.getInputStream());
        if(bufferedImage==null){
            return null;
        }

        StorePath storePath = fileStorageClient.uploadFile(file.getInputStream(), file.getSize(), suffix, null);
        System.out.println(storePath);

        return "http://image.shop.com/"+storePath.getFullPath();
    }
}
