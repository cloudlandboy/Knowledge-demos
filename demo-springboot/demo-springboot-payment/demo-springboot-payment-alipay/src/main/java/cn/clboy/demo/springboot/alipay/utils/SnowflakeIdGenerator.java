package cn.clboy.demo.springboot.alipay.utils;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * 雪花id生成器, 这个类不是单例的,会为每个使用该生成策略的实体类创建一次
 * <p>
 * 如果实现Configurable接口，可以获取@GenericGenerator注解中的参数
 */
public class SnowflakeIdGenerator implements IdentifierGenerator {

    /**
     * 不使用单例，不同表id可以重复
     */
    private Snowflake snowflake = IdUtil.createSnowflake(1, 1);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return snowflake.nextId();
    }
}