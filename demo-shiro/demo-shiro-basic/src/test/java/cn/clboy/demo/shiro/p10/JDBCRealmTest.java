package cn.clboy.demo.shiro.p10;


import cn.clboy.demo.shiro.utils.BaseTest;
import org.junit.Test;

public class JDBCRealmTest extends BaseTest {

    static final String SQL = "INSERT INTO users VALUES(NULL,'tom','4201353d6bd3307ce82b4da659e11de3','55ca1b387d48cc3bfbd02d49bb17d2d9')";
    static final String USERNAME = "tom";
    static final String PASSWORD = "68966";

    @Test
    public void jdbcRealmTest() {
        login("p10/shiro-jdbc.ini", USERNAME, PASSWORD);
        System.out.println("登录成功！");
    }
}