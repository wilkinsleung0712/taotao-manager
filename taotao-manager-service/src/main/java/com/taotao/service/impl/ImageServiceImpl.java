package com.taotao.service.impl;

import java.io.IOException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.pojo.ImageUploadResult;
import com.taotao.service.ImageService;
import com.taotao.util.FtpUtil;
import com.taotao.util.IDUtils;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;
    @Value("${FTP_USER_NAME}")
    private String FTP_USER_NAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FTP_BASE_PATH}")
    private String FTP_BASE_PATH;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;

    /*
     * (non-Javadoc)
     * 
     * @see com.taotao.service.ImageService#imageUpload(org.springframework.web.
     * multipart.MultipartFile)
     */
    @Override
    public ImageUploadResult imageUpload(MultipartFile image) {
        // get file orig name
        String origFileName = image.getOriginalFilename();
        // get image ext
        String ext = origFileName
                .substring(image.getOriginalFilename().lastIndexOf("."));
        // generate image id
        String imageName = IDUtils.genImageName();
        // folder structre /yyyy/mm/dd
        String filePath = new DateTime().toString("/yyyy/MM/dd/");
        try {
            // use util to upload image to server
            FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USER_NAME,
                    FTP_PASSWORD, FTP_BASE_PATH, filePath, imageName + ext,
                    image.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ImageUploadResult.error("Image upload failed.");
        }

        return ImageUploadResult
                .ok(IMAGE_BASE_URL + filePath + imageName + ext);
    }

}
