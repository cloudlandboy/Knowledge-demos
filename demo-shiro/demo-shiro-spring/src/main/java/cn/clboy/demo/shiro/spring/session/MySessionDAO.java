package cn.clboy.demo.shiro.spring.session;


import cn.clboy.demo.shiro.spring.mapper.SessionMapper;
import cn.clboy.demo.shiro.spring.utils.SerializableUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;

import java.io.Serializable;
import java.util.Collection;

/**
 * 自定义实现 SessionDAO
 */
public class MySessionDAO extends CachingSessionDAO {

    private SessionMapper sessionMapper;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        sessionMapper.create(sessionId, SerializableUtil.serialize(session));
        return session.getId();
    }

    @Override
    protected void doUpdate(Session session) {
        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            //如果会话过期/停止 没必要再更新了
            return;
        }
        sessionMapper.update(SerializableUtil.serialize(session), session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        sessionMapper.delete(session.getId());
    }

    /**
     * 在读取时会先查缓存中是否存在，如果找不到才到数据库中查找,见CachingSessionDAO的ReadSession方法
     */
    @Override
    protected Session doReadSession(Serializable sessionId) {
        String session = sessionMapper.querySession(sessionId);
        return SerializableUtil.deserialize(session);
    }

    /**
     * CachingSessionDAO在create方法中调用子类doCreate方法后会调用cache方法保存session到缓存中
     */
    @Override
    public Collection<Session> getActiveSessions() {
        return super.getActiveSessions();
    }

    public void setSessionMapper(SessionMapper sessionMapper) {
        this.sessionMapper = sessionMapper;
    }
}