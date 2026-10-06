package com.itheima;

import com.itheima.utils.OssTemplate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;

@Slf4j
@SpringBootTest
public class OssTest {

    @Autowired
    private OssTemplate ossTemplate;

    @Test
    public void testUpload() throws FileNotFoundException {
        //依赖本地测试图片，文件不存在时跳过，避免在其它环境执行构建时直接失败
        File file = new File("E:\\download.jpg");
        Assumptions.assumeTrue(file.exists(), "缺少本地测试文件 E:\\download.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        String filePath = ossTemplate.uploadFile(fileInputStream, "download.jpg");
        log.info("上传完成，文件地址：{}", filePath);
    }
}
