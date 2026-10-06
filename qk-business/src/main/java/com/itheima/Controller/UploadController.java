package com.itheima.Controller;

import com.itheima.dto.Result;
import com.itheima.utils.OssTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadController {

    @Autowired
    private OssTemplate ossTemplate;

    @PostMapping("/upload")
    public Result<String> uploadFile(MultipartFile image) throws Exception {
        String filePath = ossTemplate.uploadFile(image.getInputStream(), image.getOriginalFilename());
        return Result.success(filePath);
    }
}
