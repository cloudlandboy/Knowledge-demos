package cn.clboy.demo.shiro.p10;


import org.apache.shiro.realm.jdbc.JdbcRealm;

public class MyJDBCRealm extends JdbcRealm {

    public void setSaltStyle(String saltStyle) {
        SaltStyle style = Enum.valueOf(SaltStyle.class, saltStyle);
        this.setSaltStyle(style);
    }
}