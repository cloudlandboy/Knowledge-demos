package cn.clboy.demo.springcloud.httpclient.service;

import cn.clboy.demo.springcloud.httpclient.service.Mapper.UserMapper;
import cn.clboy.demo.springcloud.httpclient.service.pojo.TbUser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringcloudHttpclientServiceApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() throws JsonProcessingException {
        TbUser user = userMapper.selectByPrimaryKey(1);

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(user);
        System.out.println(jsonStr);
    }

}
