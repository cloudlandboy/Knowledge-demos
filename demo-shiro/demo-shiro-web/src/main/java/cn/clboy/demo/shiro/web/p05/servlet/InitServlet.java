package cn.clboy.demo.shiro.web.p05.servlet;


import cn.clboy.demo.shiro.web.p04.utils.JdbcTemplateUtil;
import cn.clboy.demo.shiro.web.p05.entity.Permission;
import cn.clboy.demo.shiro.web.p05.entity.Role;
import cn.clboy.demo.shiro.web.p05.entity.User;
import cn.clboy.demo.shiro.web.p05.service.PermissionService;
import cn.clboy.demo.shiro.web.p05.service.RoleService;
import cn.clboy.demo.shiro.web.p05.service.UserService;
import cn.clboy.demo.shiro.web.p05.service.impl.PermissionServiceImpl;
import cn.clboy.demo.shiro.web.p05.service.impl.RoleServiceImpl;
import cn.clboy.demo.shiro.web.p05.service.impl.UserServiceImpl;
import org.apache.shiro.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 初始化数据
 */
@WebServlet(name = "initServlet", urlPatterns = "/data/init")
public class InitServlet extends HttpServlet {
    private volatile Boolean iniEd = false;
    protected PermissionService permissionService = new PermissionServiceImpl();
    protected RoleService roleService = new RoleServiceImpl();
    protected UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Assert.isTrue(!iniEd, "数据已经初始化过了！");
        synchronized (iniEd) {
            Assert.isTrue(!iniEd, "数据已经初始化过了！");
            initData();
            iniEd = true;
        }
        resp.getWriter().write("init data success!");
    }

    public void initData() {
        String[] ddl = {
                "truncate table sys_users",
                "truncate table sys_roles",
                "truncate table sys_permissions",
                "truncate table sys_users_roles",
                "truncate table sys_roles_permissions"
        };

        //删除原来的数据
        JdbcTemplateUtil.jdbcTemplate().batchUpdate(ddl);

        //1、新增权限
        Permission p1 = new Permission("user:create", "用户模块新增", Boolean.TRUE);
        Permission p2 = new Permission("user:update", "用户模块修改", Boolean.TRUE);
        Permission p3 = new Permission("menu:create", "菜单模块新增", Boolean.TRUE);
        permissionService.createPermission(p1);
        permissionService.createPermission(p2);
        permissionService.createPermission(p3);

        //2、新增角色
        Role r1 = new Role("admin", "管理员", Boolean.TRUE);
        Role r2 = new Role("user", "用户管理员", Boolean.TRUE);
        roleService.createRole(r1);
        roleService.createRole(r2);

        //3、关联角色-权限
        roleService.correlationPermissions(r1.getId(), p1.getId());
        roleService.correlationPermissions(r1.getId(), p2.getId());
        roleService.correlationPermissions(r1.getId(), p3.getId());

        roleService.correlationPermissions(r2.getId(), p1.getId());
        roleService.correlationPermissions(r2.getId(), p2.getId());

        //4、新增用户
        User u1 = new User("zhang", "123");
        userService.createUser(u1);
        User u2 = new User("wang", "456");
        userService.createUser(u2);
        User u3 = new User("li", "789");
        userService.createUser(u3);

        //5、关联用户-角色
        userService.correlationRoles(u1.getId(), r1.getId(), r2.getId());
        userService.correlationRoles(u2.getId(), r2.getId());
    }
}