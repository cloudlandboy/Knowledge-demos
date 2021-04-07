package cn.cbloy.demo.springboot.file.upload.qiniu;


import cn.cbloy.demo.springboot.file.upload.qiniu.config.UploadClient;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UploadTest {

    @Autowired
    UploadClient uploadClient;

    @Test
    public void tokenTest() {
        for (int i = 0; i < 5; i++) {
            String uploadToken = uploadClient.getUploadToken();
            System.out.println(uploadToken);
        }
    }

    @Test
    public void uploadFileTest() throws IOException {
        InputStream inputStream = new ClassPathResource("images/001.jpg").getInputStream();
        try {
            Response response = uploadClient.uploadFile(inputStream);
            boolean ok = response.isOK();
            System.out.println(ok);
            Map resBody = new Gson().fromJson(response.bodyString(), Map.class);
            System.out.println(resBody);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
}