package cn.cbloy.demo.springboot.file.upload.qiniu.config;


import com.qiniu.storage.Region;
import com.qiniu.util.Json;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 七牛云配置
 *
 * @Author clboy
 * @Date 2021/3/29 下午11:30
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = "upload.qiniu")
public class UploadProperties implements InitializingBean {

    public static final int DEFAULT_EXPIRESECONDS = 3600;
    /**
     * 七牛云 access key
     * <p>
     * 密钥管理中心获取：
     *
     * <a>https://portal.qiniu.com/user/key<a/>
     */
    private String accessKey;


    /**
     * 七牛云 secret key
     * <p>
     * 密钥管理中心获取：
     *
     * <a>https://portal.qiniu.com/user/key<a/>
     */
    private String secretKey;

    /**
     * 七牛云 bucket
     * 需要先创建好
     */
    private String bucket;

    /**
     * 文件名前缀
     * 会被policy.saveKey和上传文件时指定的key所覆盖
     * 当policy.forceSaveKey为true时，必须设置prefix或者policy.saveKey（而选一），此时不会被key所覆盖
     */
    private String prefix;

    /**
     * 存储支空间所在的机房，可选值：
     * 华东，
     * 华北，
     * 华南，
     * 北美，
     * 东南亚
     */
    private String region;

    /**
     * 七牛云 域名
     */
    private String domain;

    /**
     * 凭证的有效时长，默认3600
     */
    private int expireSeconds = DEFAULT_EXPIRESECONDS;

    /**
     * 上传策略
     * <p>
     * 上传策略是资源上传时附带的一组配置设定。通过这组配置信息，七牛云存储可以了解用户上传的需求：它将上传什么资源，上传到哪个空间，上传结果是回调通知还是使用重定向跳转，是否需要设置反馈信息的内容，以及授权上传的截止时间等等。
     * <p>
     * 上传策略同时还参与请求验证，可以验证用户对某个资源的上传请求是否完整。
     * <p>
     * 见：
     * <a>https://developer.qiniu.com/kodo/1206/put-policy#save-key<a/>
     * <p>
     * scope和deadline无需配置，由程序根据bucket和expireSeconds生成。
     * <p>
     * 上传策略的ReturnBody和callbackBody字段内容中使用变量:
     * <p>
     * <a>https://developer.qiniu.com/kodo/1235/vars#magicvar</a>
     */
    private Map<String, Object> policy;


    /**
     * 区域名与获取 Region的方法名映射
     */
    private static Map<String, String> regionMap = new HashMap<>(7);

    static {
        regionMap.put("华东", "region0");
        regionMap.put("华北", "region1");
        regionMap.put("华南", "region2");
        regionMap.put("北美", "regionNa0");
        regionMap.put("东南亚", "regionAs0");
    }

    public String getAccessKey() {

        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public Map<String, Object> getPolicy() {
        return policy;
    }

    public void setPolicy(Map<String, Object> policy) {
        this.policy = policy;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    static Region toRegionObj(String regionName) {
        Region region = null;
        try {
            String method = regionMap.get(regionName);
            region = (Region) (Region.class.getMethod(method).invoke(null));
        } catch (Exception e) {
            throw new RuntimeException("get region error!");
        }
        return region;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Object returnBody = this.policy.get("returnBody");

        // 转为json字符串（必须，有的参数格式就要求是json格式字符串）
        if (returnBody instanceof Map) {
            this.policy.put("returnBody", Json.encode(returnBody));
        }

        //添加文件前缀
        if (this.prefix != null && !this.policy.containsKey("saveKey")) {
            this.policy.put("saveKey", this.prefix + "$(etag)");
        }
        //其他参数需要转的也在这里转...

    }
}