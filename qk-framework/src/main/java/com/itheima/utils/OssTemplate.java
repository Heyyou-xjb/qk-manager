package com.itheima.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.itheima.properties.OssProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.InputStream;

@Slf4j
@Component
public class OssTemplate {

    @Autowired
    private OssProperties ossProperties;

    public String uploadFile(InputStream fileStream, String fileName) {


        // 创建凭证提供者
        DefaultCredentialProvider provider = new DefaultCredentialProvider(ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());

        // 配置客户端参数
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        // 显式声明使用V4签名算法
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);

        // 初始化OSS客户端
        OSS ossClient = OSSClientBuilder.create()
                .credentialsProvider(provider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(ossProperties.getRegion())
                .endpoint(ossProperties.getEndpoint())
                .build();

        try {

            //meta设置请求头,解决访问图片地址直接下载
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(getContentType(FileUtil.extName(fileName)));

            //使用UUID重命名并放入统一目录，避免同名文件互相覆盖
            String extName = FileUtil.extName(fileName);
            String finalFileName = "hahaha/" + IdUtil.fastSimpleUUID() + (StrUtil.isBlank(extName) ? "" : "." + extName);

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossProperties.getBucketName(), finalFileName, fileStream, meta);

            ossClient.putObject(putObjectRequest);

            // https://qk-lpt.oss-cn-beijing.aliyuncs.com/1.png
            String filePath = "http://" + ossProperties.getBucketName() + "." + ossProperties.getEndpoint() + "/" + finalFileName;

            return filePath;

        } catch (Exception oe) {
            log.error("Error Message:" + oe.getMessage());
        } finally {
            ossClient.shutdown();
        }

        return null;

    }

    //文件后缀处理
    private String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase("png")) {
            return "image/png";
        }
        if (FilenameExtension.equalsIgnoreCase("jpeg") ||
                FilenameExtension.equalsIgnoreCase("jpg")) {
            return "image/jpeg";
        }
        return "image/jpeg";
    }
}
