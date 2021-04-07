package cn.cbloy.demo.springboot.file.upload.qiniu.controller;


import cn.cbloy.demo.springboot.file.upload.qiniu.response.UploadResponse;
import cn.cbloy.demo.springboot.file.upload.qiniu.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 单文件上传,前端异步上传是把文件一个个上传，不是一起传过来的，调用此接口即可
     * 使用elementUI的回显功能，上传失败要设置状态码
     */
    @RequestMapping(value = "/single", method = {RequestMethod.POST, RequestMethod.PUT})
    public UploadResponse uploadAvatar(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletResponse response) throws IOException {
        if (file == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return UploadResponse.error("文件不能为空！");
        }

        UploadResponse uploadResponse = uploadService.uploadFile(file);

        if (!uploadResponse.isSuccess()) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return uploadResponse;
    }
}