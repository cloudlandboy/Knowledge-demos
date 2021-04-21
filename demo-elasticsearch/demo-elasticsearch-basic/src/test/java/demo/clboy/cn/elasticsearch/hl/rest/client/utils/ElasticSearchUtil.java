package demo.clboy.cn.elasticsearch.hl.rest.client.utils;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticSearchUtil {
    public static final String[] ADDRESS_ARRAY = {"192.168.46.88:9200"};
    public static HttpHost[] HttpHosts;


    static {
        //创建HttpHost数组，其中存放es主机和端口的配置信息
        HttpHosts = new HttpHost[ADDRESS_ARRAY.length];
        for (int i = 0; i < ADDRESS_ARRAY.length; i++) {
            String[] ipAndPort = ADDRESS_ARRAY[i].split(":");
            HttpHosts[i] = new HttpHost(ipAndPort[0], Integer.parseInt(ipAndPort[1]), "http");
        }
    }


    /**
     * 创建RestHighLevelClient客户端
     */
    public static RestHighLevelClient getRestHighLevelClient() {
        return new RestHighLevelClient(RestClient.builder(HttpHosts));
    }


    /**
     * 创建RestClient 低级客户端
     */
    public static RestClient getRestClient() {
        return RestClient.builder(HttpHosts).build();
    }

}