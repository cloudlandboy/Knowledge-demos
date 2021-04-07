package cn.cbloy.demo.springboot.file.upload.fastdfs.exception;


import cn.cbloy.demo.springboot.file.upload.fastdfs.response.UploadResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public UploadResponse handle(MaxUploadSizeExceededException exception) {
        System.out.println(exception.getMessage());
        return UploadResponse.error("文件最大不能超过4MB");
    }
}