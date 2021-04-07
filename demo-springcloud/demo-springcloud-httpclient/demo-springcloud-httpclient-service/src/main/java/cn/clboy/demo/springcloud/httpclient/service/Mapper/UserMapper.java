package cn.clboy.demo.springcloud.httpclient.service.Mapper;

import cn.clboy.demo.springcloud.httpclient.service.pojo.TbUser;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author cloudlandboy
 * @Date 2019/11/25 上午10:15
 * @Since 1.0.0
 */
@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<TbUser> {

}