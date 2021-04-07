package cn.clboy.demo.springcloud.helloworld.provider.service;


import cn.clboy.demo.springcloud.helloworld.provider.pojo.TbUser;

/**
 * @Author cloudlandboy
 * @Date 2019/11/25 下午4:59
 * @Since 1.0.0
 */

public interface IUserService {

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    public TbUser queryById(Long id);
}