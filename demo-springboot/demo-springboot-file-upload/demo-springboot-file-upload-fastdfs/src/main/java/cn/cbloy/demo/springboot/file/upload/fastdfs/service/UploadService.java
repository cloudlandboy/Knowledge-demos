package cn.cbloy.demo.springboot.file.upload.fastdfs.service;


import cn.cbloy.demo.springboot.file.upload.fastdfs.response.UploadResponse;
import cn.hutool.core.io.FileUtil;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UploadService {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;

    @Value("${upload.storage-server-domain}")
    private String storageServerDomain;

    public UploadResponse uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(), FileUtil.extName(file.getOriginalFilename()), null);
        String thumbImagePath = storageServerDomain + storePath.getGroup() + "/" + thumbImageConfig.getThumbImagePath(storePath.getPath());
        String path = storageServerDomain + storePath.getFullPath();
        return UploadResponse.ok(path, thumbImagePath);
    }
}