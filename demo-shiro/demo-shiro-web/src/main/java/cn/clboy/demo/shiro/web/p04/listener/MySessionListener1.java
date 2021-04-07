package cn.clboy.demo.shiro.web.p04.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * 会话监听器用于监听会话创建、过期及停止事件
 */
public class MySessionListener1 implements SessionListener {

    /**
     * 会话创建时触发
     */
    @Override
    public void onStart(Session session) {
        System.out.println(getClass().getName() + "会话创建：" + session.getId());
    }

    /**
     * 会话过期时触发，cookie中有sessionId，服务器上的已经超时过期
     */
    @Override
    public void onExpiration(Session session) {
        System.out.println(getClass().getName() + "会话过期：" + session.getId());
    }

    /**
     * 退出/会话过期时触发，调用 session.invalidate()
     */
    @Override
    public void onStop(Session session) {
        System.out.println(getClass().getName() + "会话停止：" + session.getId());
    }
}