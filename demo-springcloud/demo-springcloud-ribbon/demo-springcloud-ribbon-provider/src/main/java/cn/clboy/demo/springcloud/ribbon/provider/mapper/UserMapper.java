package cn.clboy.demo.springcloud.ribbon.provider.mapper;


import cn.clboy.demo.springcloud.ribbon.provider.pojo.TbUser;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Author cloudlandboy
 * @Date 2019/11/25 下午4:57
 * @Since 1.0.0
 */
@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<TbUser> {

}