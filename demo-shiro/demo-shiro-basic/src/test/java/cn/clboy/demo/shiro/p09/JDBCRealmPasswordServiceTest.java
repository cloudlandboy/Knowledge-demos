package cn.clboy.demo.shiro.p09;


import cn.clboy.demo.shiro.utils.BaseTest;
import org.apache.shiro.subject.Subject;

import java.util.HashMap;
import java.util.Map;

public class JDBCRealmPasswordServiceTest extends BaseTest {


    public static void main(String[] args) {
        new JDBCRealmPasswordServiceTest().testPasswordServiceWithJdbcRealmAndMyRealm();
    }

    public void testPasswordServiceWithJdbcRealmAndMyRealm() {
        Map<String, String> users = new HashMap<>(3);
        users.put("zhang", "123");
        users.put("wu", "123");
        users.put("liu", "123");
        users.forEach((username, password) -> {
            new Thread(() -> {
                Subject subject = login("p9/shiro-jdbc-passwordservice.ini", username, password);
                System.out.println(subject.getPrincipal() + "成功登录！");
                tearDown();
            }).start();
        });
    }
}