package cn.clboy.demo.hibernate.spring.config;

import org.hibernate.dialect.MySQLDialect;

/**
 * @Author clboy
 * @Description 配置自动建表使用utf8编码
 **/
public class MySQL5DialectUTF8 extends MySQLDialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}