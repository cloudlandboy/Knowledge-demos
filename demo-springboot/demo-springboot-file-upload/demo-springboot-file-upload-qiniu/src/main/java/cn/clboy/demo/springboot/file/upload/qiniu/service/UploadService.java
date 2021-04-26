package cn.clboy.demo.springboot.file.upload.qiniu.service;


import cn.clboy.demo.springboot.file.upload.qiniu.config.UploadClient;
import cn.clboy.demo.springboot.file.upload.qiniu.response.UploadResponse;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class UploadService {

    @Autowired
    private UploadClient uploadClient;

    public UploadResponse uploadFile(MultipartFile file) throws IOException {
        try {
            Response response = uploadClient.uploadFile(file.getInputStream());
            if (response.isOK()) {
                Map resBody = Json.decode(response.bodyString(), Map.class);
                String key = resBody.get("key").toString();
                UploadResponse uploadResponse = UploadResponse.ok(uploadClient.getUrl(key), null);
                return uploadResponse;
            }
            return UploadResponse.error(response.error);
        } catch (QiniuException qiniuEx) {
            return UploadResponse.error(qiniuEx.error());
        } catch (Exception e) {
            return UploadResponse.error("未知错误！");
        }
    }
}