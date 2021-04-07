package cn.cbloy.demo.springboot.file.upload.local.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadResponse {
    private boolean success;
    private String msg;
    private String path;


    public static UploadResponse ok(String path) {
        return new UploadResponse(true, path);
    }

    public static UploadResponse error(String msg) {
        return new UploadResponse(false, msg);
    }

    public UploadResponse(boolean success, String res) {
        this.success = success;
        if (success) {
            this.path = res;
        } else {
            this.msg = res;
        }
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
}