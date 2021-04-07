package cn.clboy.demo.springcloud.ribbon.provider.service.impl;

import cn.clboy.demo.springcloud.ribbon.provider.mapper.UserMapper;
import cn.clboy.demo.springcloud.ribbon.provider.pojo.TbUser;
import cn.clboy.demo.springcloud.ribbon.provider.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author cloudlandboy
 * @Date 2019/11/25 下午5:04
 * @Since 1.0.0
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public TbUser queryById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}