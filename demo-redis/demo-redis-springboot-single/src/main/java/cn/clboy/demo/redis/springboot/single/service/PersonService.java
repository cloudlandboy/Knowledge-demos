package cn.clboy.demo.redis.springboot.single.service;


import cn.clboy.demo.redis.springboot.single.pojo.Person;
import cn.clboy.demo.redis.springboot.single.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * CacheConfig 统一指定缓存的缓存的名称
 * 在redis中相当于前缀："PERSON::id"
 */
@Service
@CacheConfig(cacheNames = "PERSON")
public class PersonService {

    @Autowired
    RedisUtil<String, Object> redisUtil;


    /**
     * 模拟数据库
     */
    private Map<String, Person> personTable = new HashMap<>();

    {
        personTable.put("1", new Person("1", "张三", "他的头发有两寸来长，乱蓬蓬的，活像一个喜鹊窝", 31));
        personTable.put("2", new Person("2", "李四", "他的两只耳朵出奇的大", 23));
        personTable.put("3", new Person("3", "王二麻子", "他那半寸长的短发像秋天的芦草一样又干又硬", 22));
        personTable.put("4", new Person("4", "冷希月", "他的一对耳朵啊，活像两片神气活现地撑开着的河蚌壳儿！", 30));
    }


    @CachePut(key = "#person.id")
    public Person saveOrUpdate(Person person) {
        String id = personTable.size() + 1 + "";
        person.setId(id);
        System.out.println(id + " 添加到数据库成功!");

        //手动添加缓存
        //redisUtil.set("PERSON:" + id, person);

        return person;
    }

    @Cacheable(key = "#id")
    public Person getPerson(String id) {
        System.out.println("到数据库查询...");
        return personTable.get(id);
    }


    @CacheEvict(key = "#id")
    public void delete(String id) {
        personTable.remove(id);
        System.out.println("用户已经删除...");
    }
}