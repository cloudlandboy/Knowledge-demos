package cn.clboy.demo.redis.springboot.single.config;


import cn.clboy.demo.redis.springboot.single.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig implements RedisCacheManagerBuilderCustomizer {

    /**
     * 默认情况下的模板只能支持RedisTemplate<Object, Object>，jdk序列化
     * 和 stringRedisTemplate<String, String>，也就是只能存入字符串
     * <p>
     * 注入自定义的jsonRedisTemplate
     * <p>
     * 如果定义的bean名称为 redisTemplate，自动配置的redisTemplate就不会生效
     *
     * @see RedisAutoConfiguration#redisTemplate
     */
    @Bean("jsonRedisTemplate")
    public RedisTemplate<String, Object> jsonRedisTemplate(RedisConnectionFactory redisConnectionFactory) {

        // 将template 泛型设置为 <String, Object>
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

        // 序列化设置
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        // value、hash的value 采用 Jackson 序列化方式
        template.setValueSerializer(RedisSerializer.json());
        template.setHashValueSerializer(RedisSerializer.json());
        template.afterPropertiesSet();

        return template;
    }

    /**
     * 添加封装好的工具类
     *
     * @param jsonRedisTemplate
     * @return
     */
    @Bean
    public RedisUtil<String, Object> redisUtil(@Qualifier("jsonRedisTemplate") RedisTemplate<String, Object> jsonRedisTemplate) {
        return new RedisUtil<>(jsonRedisTemplate);
    }

    /**
     * 订阅/发布，将订阅者（MessageListener实例）添加到RedisMessageListenerContainer中然后注入到容器中去
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);

        // 订阅指定的channel
        redisMessageListenerContainer.addMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] pattern) {
                System.out.println("message.getChannel(): " + new String(message.getChannel()));
                System.out.println("message.getBody(): " + new String(message.getBody()));
                System.out.println("pattern: " + new String(pattern));
            }
        }, ChannelTopic.of("test-queue"));
        return redisMessageListenerContainer;
    }

    /**
     * 监听key过期，注入到容器中即可
     * <p>
     * redis服务器也要配置notify-keyspace-events
     * 参考：
     * <a>https://redis.io/topics/notifications<a/>
     * <p>
     * 而在springboot中使用 KeyspaceEventMessageListener 类型监听时可以不用配置，spring会帮我们配置
     * 见其init方法
     * @see org.springframework.data.redis.listener.KeyspaceEventMessageListener#init()
     */
    @Bean
    public KeyExpireMessageListener keyExpireMessageListener(RedisMessageListenerContainer redisMessageListenerContainer) {
        return new KeyExpireMessageListener(redisMessageListenerContainer);
    }


    /**
     * 自定义CacheManager 设置序列化方式为json
     * <p>
     * "org.springframework.boot.autoconfigure.cache.RedisCacheConfiguration" 生效的条件
     *
     * <p>
     * 与下面实现RedisCacheManagerBuilderCustomizer#customize 方法二选一
     * <p>
     * 如果没有生效，查看添加缓存的方法有没有返回值
     * <p>
     * 两种方法都会使配置文件中配置的过期时间，key前缀等失效，需要重新配置
     */
    //@Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager.RedisCacheManagerBuilder cacheManagerBuilder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));

        RedisCacheManager redisCacheManager = cacheManagerBuilder.cacheDefaults(redisCacheConfiguration).build();
        return redisCacheManager;
    }

    /**
     * 实现RedisCacheManagerBuilderCustomizer的customize方法，启动时由spring调用
     * <p>
     * 设置缓存注解的value的序列化方式为json，默认jdk序列化
     *
     * @param builder
     */
    @Override
    public void customize(RedisCacheManager.RedisCacheManagerBuilder builder) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()))//设置value序列化方式为json
                .entryTtl(Duration.ofMinutes(1)); //设置1分钟过期
        builder.cacheDefaults(redisCacheConfiguration);
    }
}