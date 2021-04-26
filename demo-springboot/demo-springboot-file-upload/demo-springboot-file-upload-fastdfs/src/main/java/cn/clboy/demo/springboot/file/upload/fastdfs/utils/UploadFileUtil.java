package cn.clboy.demo.springboot.file.upload.fastdfs.utils;


import cn.hutool.core.io.FileUtil;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class UploadFileUtil {
    public static Set<MetaData> fileMetaData(File file) {
        Set<MetaData> metaDataSet = new HashSet<>();
        metaDataSet.add(new MetaData("name", file.getName()));
        metaDataSet.add(new MetaData("ext", FileUtil.extName(file)));
        metaDataSet.add(new MetaData("type", FileUtil.getType(file)));
        metaDataSet.add(new MetaData("size", Long.toString(file.length())));
        return metaDataSet;
    }
}