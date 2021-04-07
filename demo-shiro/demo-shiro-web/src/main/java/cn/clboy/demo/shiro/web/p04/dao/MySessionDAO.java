package cn.clboy.demo.shiro.web.p04.dao;


import cn.clboy.demo.shiro.web.p04.utils.JdbcTemplateUtil;
import cn.clboy.demo.shiro.web.p04.utils.SerializableUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 自定义实现 SessionDAO
 */
public class MySessionDAO extends CachingSessionDAO {

    private JdbcTemplate jdbcTemplate = JdbcTemplateUtil.jdbcTemplate();

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        String sql = "insert into sessions(id, session) values(?,?)";
        jdbcTemplate.update(sql, sessionId, SerializableUtil.serialize(session));
        return session.getId();
    }

    @Override
    protected void doUpdate(Session session) {
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            //如果会话过期/停止 没必要再更新了
            return;
        }
        String sql = "update sessions set session=? where id=?";
        jdbcTemplate.update(sql, SerializableUtil.serialize(session), session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        String sql = "delete from sessions where id=?";
        jdbcTemplate.update(sql, session.getId());
    }

    /**
     * 在读取时会先查缓存中是否存在，如果找不到才到数据库中查找,见CachingSessionDAO的ReadSession方法
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        String sql = "select session from sessions where id=?";
        List<String> sessionStrList = jdbcTemplate.queryForList(sql, String.class, sessionId);
        if (sessionStrList.size() == 0) {
            return null;
        }
        return SerializableUtil.deserialize(sessionStrList.get(0));
    }

    /**
     * CachingSessionDAO在create方法中调用子类doCreate方法后会调用cache方法保存session到缓存中
     */
    @Override
    public Collection<Session> getActiveSessions() {
        return super.getActiveSessions();
    }
}