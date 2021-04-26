package cn.clboy.demo.springboot.file.upload.fastdfs.service;

import cn.clboy.demo.springboot.file.upload.fastdfs.utils.UploadFileUtil;
import cn.hutool.core.io.FileUtil;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.fdfs.ThumbImageConfig;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * FastFileStorageClient客户端
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FastFileStorageClientTest {

    @Autowired
    protected FastFileStorageClient storageClient;

    @Autowired
    private ThumbImageConfig thumbImageConfig;
    protected static Logger LOGGER = LoggerFactory.getLogger(FastFileStorageClientTest.class);

    /**
     * 上传文件，并且设置MetaData
     */
    @Test
    public void testUploadFileAndMetaData() throws IOException {

        File file = new ClassPathResource("images/001.jpg").getFile();
        Set<MetaData> metaData = UploadFileUtil.fileMetaData(file);

        //inputStream，文件大小，文件后缀，MetaData
        StorePath path = storageClient.uploadFile(new FileInputStream(file), file.length(), FileUtil.extName(file), metaData);
        LOGGER.info("上传文件路径{}", path);

        // 验证获取MetaData
        LOGGER.info("##获取Metadata##");
        Set<MetaData> fetchMetaData = storageClient.getMetadata(path.getGroup(), path.getPath());
        assertEquals(fetchMetaData, metaData);

        LOGGER.info(metaData.toString());
    }

    /**
     * 不带MetaData也应该能上传成功
     */
    @Test
    public void testUploadFileWithoutMetaData() throws IOException {
        File file = new ClassPathResource("images/002.jpg").getFile();

        // 上传文件和Metadata
        StorePath path = storageClient.uploadFile(new FileInputStream(file), file.length(), FileUtil.extName(file), null);
        LOGGER.info(path.getFullPath());
    }


    /**
     * 删除文件
     */
    @Test
    public void testDeleteFile() {
        String filePath = "group1/M00/00/00/wKguY2BhiZ2ARX3DAALhXg-WaKI549.jpg";
        storageClient.deleteFile(filePath);
    }


    /**
     * 上传图片，并且生成缩略图
     * <p>
     * 缩略图为上传文件名+缩略图后缀 _150x150,如 xxx.jpg,缩略图为 xxx_150x150.jpg
     * <p>
     * 实际样例如下
     * <p>
     * <pre>
     *     http://192.168.46.99/group1/M00/00/17/rBEAAl33pQaAWNQNAAHYvQQn-YE374.jpg
     *     http://192.168.46.99/group1/M00/00/17/rBEAAl33pQaAWNQNAAHYvQQn-YE374_150x150.jpg
     * </pre>
     */
    @Test
    public void testUploadImageAndCrtThumbImage() throws IOException {
        File imgFile = new ClassPathResource("images/002.jpg").getFile();
        StorePath storePath = storageClient.uploadImageAndCrtThumbImage(new FileInputStream(imgFile), imgFile.length(), FileUtil.extName(imgFile), null);
        LOGGER.info("上传文件路径：{}", storePath.getFullPath());

        // 这里需要一个获取从文件名的能力，所以文件名配置以后就最好不要改了
        String slavePath = thumbImageConfig.getThumbImagePath(storePath.getPath());
        LOGGER.info("缩略图路径：{}", slavePath);
    }

}