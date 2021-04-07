package cn.clboy.demo.shiro.spring.mapper;

import org.apache.ibatis.annotations.*;

import java.io.Serializable;

/**
 * @Author clboy
 * @Date 2021/2/1 下午2:00
 * @Since 1.0.0
 */
public interface SessionMapper {

    @Insert("INSERT INTO sessions(id, session) VALUES(#{sessionId},#{session})")
    void create(@Param("sessionId") Serializable sessionId, @Param("session") String session);

    @Update("UPDATE sessions SET session=#{session} where id=#{id}")
    void update(@Param("session") String session, @Param("id") Serializable sessionId);

    @Delete("DELETE FROM sessions WHERE id=#{sessionId}")
    void delete(Serializable sessionId);

    @Select("SELECT session FROM sessions WHERE id=#{value}")
    String querySession(Serializable sessionId);
}