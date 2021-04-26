package cn.clboy.demo.springboot.file.upload.local.service;


import cn.clboy.demo.springboot.file.upload.local.utils.UploadFileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadService {

    @Value("${upload.avatarSavePath}")
    private String avatarSavePath;

    public String saveAvatar(MultipartFile avatar) throws IOException {
        return UploadFileUtil.saveFileToClassPath(avatar, "/static", avatarSavePath);
    }
}