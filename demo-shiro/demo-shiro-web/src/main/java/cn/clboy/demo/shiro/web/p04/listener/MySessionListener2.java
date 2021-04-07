package cn.clboy.demo.shiro.web.p04.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;

/**
 * 如果只想监听某一个事件，可以继承 SessionListenerAdapter 实现：
 */
public class MySessionListener2 extends SessionListenerAdapter {

    @Override
    public void onStart(Session session) {
        System.out.println(getClass().getName() + "会话创建：" + session.getId());
    }
}