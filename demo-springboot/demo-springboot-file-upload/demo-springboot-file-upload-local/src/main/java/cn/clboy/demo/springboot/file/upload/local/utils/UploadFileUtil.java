package cn.clboy.demo.springboot.file.upload.local.utils;


import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class UploadFileUtil {


    /**
     * @param file
     * @param staticPath      classpath下静态资源文件夹
     * @param relativeDirPath 相对于静态资源文件夹的文件夹路径
     * @return
     * @throws IOException
     */
    public static String saveFileToClassPath(MultipartFile file, String staticPath, String relativeDirPath) throws IOException {

        //获取classpath绝对地址
        ClassPathResource classPathResource = new ClassPathResource("");
        String classPath = classPathResource.getFile().getAbsolutePath();

        //使用MD5对文件去重
        String md5 = SecureUtil.md5(file.getInputStream());

        //web访问文件地址
        StringBuilder webPath = new StringBuilder(relativeDirPath)
                .append(md5)
                .append(".")
                .append(FileUtil.extName(file.getOriginalFilename()));

        //文件保存绝对路径
        StringBuilder fileSavePath = new StringBuilder(classPath)
                .append(staticPath)
                .append(webPath);

        File saveFile = new File(fileSavePath.toString());

        //校验文件是否存在
        if (saveFile.exists()) {
            return webPath.toString();
        }

        File dir = saveFile.getParentFile();

        // 目录不存在创建目录
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //保存文件
        file.transferTo(saveFile);

        return webPath.toString();
    }
}