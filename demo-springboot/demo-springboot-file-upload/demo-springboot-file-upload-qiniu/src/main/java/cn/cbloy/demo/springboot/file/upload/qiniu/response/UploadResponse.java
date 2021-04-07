package cn.cbloy.demo.springboot.file.upload.qiniu.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadResponse {
    private boolean success;
    private String msg;
    private String path;
    private String thumb;

    public static UploadResponse ok(String path, String thumb) {
        return new UploadResponse(true, path, thumb);
    }

    public static UploadResponse error(String msg) {
        return new UploadResponse(false, msg);
    }

    public UploadResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public UploadResponse(boolean success, String path, String thumb) {
        this.success = success;
        this.path = path;
        this.thumb = thumb;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}