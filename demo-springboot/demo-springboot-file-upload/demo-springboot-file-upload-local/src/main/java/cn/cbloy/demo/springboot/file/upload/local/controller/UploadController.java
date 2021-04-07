package cn.cbloy.demo.springboot.file.upload.local.controller;


import cn.cbloy.demo.springboot.file.upload.local.response.UploadResponse;
import cn.cbloy.demo.springboot.file.upload.local.service.UploadService;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @Value("${upload.allowAvatarFileType}")
    private List<String> allowAvatarFileType;

    /**
     * 单文件上传
     */
    @RequestMapping(value = "/avatar", method = {RequestMethod.POST, RequestMethod.PUT})
    public UploadResponse uploadAvatar(MultipartFile avatar) throws IOException {
        if (avatar == null) {
            return UploadResponse.error("上传文件不能为空！");
        }
        //文件校验 ...
        String type = FileTypeUtil.getType(avatar.getInputStream());
        if (!allowAvatarFileType.contains(type)) {
            return UploadResponse.error("文件格式不正确！");
        }
        String path = uploadService.saveAvatar(avatar);
        return UploadResponse.ok(path);
    }


    /**
     * 多文件上传
     */
    @RequestMapping(value = "/image", method = {RequestMethod.POST, RequestMethod.PUT})
    public List<UploadResponse> uploadAvatar(MultipartFile[] images) throws IOException {

        ArrayList<UploadResponse> uploadResponses = new ArrayList<>(Math.max(images.length, 1));

        if (ArrayUtil.isEmpty(images)) {
            uploadResponses.add(UploadResponse.error("上传文件不能为空！"));
            return uploadResponses;
        }
        for (MultipartFile image : images) {
            //文件校验 ...
            String type = FileTypeUtil.getType(image.getInputStream());
            if (!allowAvatarFileType.contains(type)) {
                uploadResponses.add(UploadResponse.error("文件格式不正确！"));
                continue;
            }
            String path = uploadService.saveAvatar(image);
            uploadResponses.add(UploadResponse.ok(path));
        }

        return uploadResponses;
    }
}