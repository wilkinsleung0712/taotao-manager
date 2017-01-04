package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.service.ImageService;

@Controller
@RequestMapping("/pic")
public class ImageController {
    
    @Autowired
    private ImageService imageService;
    
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile uploadFile) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(imageService.imageUpload(uploadFile));
    }
}
