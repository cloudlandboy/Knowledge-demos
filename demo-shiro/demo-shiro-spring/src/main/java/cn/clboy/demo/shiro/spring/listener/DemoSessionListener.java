package cn.clboy.demo.shiro.spring.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author clboy
 * @Date 2021/2/2 上午10:37
 * @Since 1.0.0
 */

public class DemoSessionListener implements SessionListener {

    private final Logger LOGGER = LoggerFactory.getLogger(DemoSessionListener.class);

    @Override
    public void onStart(Session session) {
        log(session, "创建session");
    }

    @Override
    public void onStop(Session session) {
        log(session, "session停止");
    }

    @Override
    public void onExpiration(Session session) {
        log(session, "session过期");
    }

    public void log(Session session, String type) {
        Object[] args = {type, session.getId(), session.getStartTimestamp(), session.getLastAccessTime(), session.getTimeout()};
        LOGGER.info("{} - [Id:{},StartTimestamp:{},LastAccessTime:{},Timeout:{}]", args);
    }
}