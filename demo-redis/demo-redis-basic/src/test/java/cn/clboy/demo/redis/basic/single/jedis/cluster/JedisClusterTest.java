package cn.clboy.demo.redis.basic.single.jedis.cluster;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;

/**
 * jedis连接redis集群
 */

public class JedisClusterTest {

    @Test
    public void Test() throws Exception {
        HashSet<HostAndPort> hostAndPorts = new HashSet<>();
        hostAndPorts.add(new HostAndPort("127.0.0.1", 6379));
        hostAndPorts.add(new HostAndPort("192.168.46.11", 6379));

        JedisCluster jedisCluster = new JedisCluster(hostAndPorts);
        String status = jedisCluster.set("key001", "value001");
        System.out.println(status);
    }
}