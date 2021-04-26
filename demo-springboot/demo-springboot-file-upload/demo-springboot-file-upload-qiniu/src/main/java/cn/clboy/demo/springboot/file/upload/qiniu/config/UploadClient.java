package cn.clboy.demo.springboot.file.upload.qiniu.config;


import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用于上传的工具类
 *
 * @Author clboy
 * @Date 2021/3/29 下午11:30
 * @since 1.0.0
 */
@Component
@EnableConfigurationProperties(UploadProperties.class)
public class UploadClient {

    private UploadProperties uploadProperties;

    private Auth auth;

    private StringMap policy;

    private UploadManager uploadManager;


    @Autowired
    public UploadClient(UploadProperties uploadProperties) {
        this.uploadProperties = uploadProperties;
        this.auth = Auth.create(uploadProperties.getAccessKey(), uploadProperties.getSecretKey());
        this.policy = new StringMap(uploadProperties.getPolicy());
        Region region = UploadProperties.toRegionObj(uploadProperties.getRegion());
        this.uploadManager = new UploadManager(new Configuration(region));
    }

    public String getUploadToken() {
        return this.getUploadToken(null);
    }

    /**
     * 表示只允许用户上传指定 key 的文件
     *
     * @param key
     * @return
     */
    public String getUploadToken(String key) {
        return this.auth.uploadToken(uploadProperties.getBucket(), key, uploadProperties.getExpireSeconds(), policy);
    }

    public Response uploadFile(File file) throws IOException {
        return this.uploadFile(new FileInputStream(file), null);
    }

    public Response uploadFile(File file, String key) throws IOException {
        return this.uploadFile(new FileInputStream(file), key);
    }

    public Response uploadFile(InputStream inputStream) throws IOException {
        return this.uploadFile(inputStream, null);
    }

    public Response uploadFile(InputStream inputStream, String key) throws IOException {
        return uploadManager.put(inputStream, key, this.getUploadToken(), null, null);
    }

    /**
     * 返回访问链接(域名+key)
     */
    public String getUrl(String key) {
        return this.uploadProperties.getDomain().concat(key);
    }

}