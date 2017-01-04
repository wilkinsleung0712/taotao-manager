package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import com.taotao.pojo.ImageUploadResult;

public interface ImageService {
    /**
     * @param image
     * @return
     */
    public ImageUploadResult imageUpload(MultipartFile image);
    
    //TODO: we need to add download image as well.
}
