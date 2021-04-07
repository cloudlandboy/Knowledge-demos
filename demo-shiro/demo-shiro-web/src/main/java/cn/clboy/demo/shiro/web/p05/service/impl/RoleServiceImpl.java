package cn.clboy.demo.shiro.web.p05.service.impl;


import cn.clboy.demo.shiro.web.p05.dao.RoleDao;
import cn.clboy.demo.shiro.web.p05.dao.impl.RoleDaoImpl;
import cn.clboy.demo.shiro.web.p05.entity.Role;
import cn.clboy.demo.shiro.web.p05.service.RoleService;

public class RoleServiceImpl implements RoleService {

    private RoleDao roleDao = new RoleDaoImpl();


    public Role createRole(Role role) {
        return roleDao.createRole(role);
    }

    public void deleteRole(Long roleId) {
        roleDao.deleteRole(roleId);
    }

    /**
     * 添加角色-权限之间关系
     *
     * @param roleId
     * @param permissionIds
     */
    public void correlationPermissions(Long roleId, Long... permissionIds) {
        roleDao.correlationPermissions(roleId, permissionIds);
    }

    /**
     * 移除角色-权限之间关系
     *
     * @param roleId
     * @param permissionIds
     */
    public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
        roleDao.uncorrelationPermissions(roleId, permissionIds);
    }

}
