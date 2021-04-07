package cn.clboy.demo.shiro.spring.service;


import cn.clboy.demo.shiro.spring.dto.RoleInfoDTO;
import cn.clboy.demo.shiro.spring.entity.Role;

import java.util.List;

/**
 * @Author clboy
 * @Description 角色管理
 **/
public interface RoleService {


    /**
     * 添加角色
     *
     * @param role
     * @return Role
     **/
    Role createRole(Role role);

    /**
     * 删除角色
     *
     * @param roleId
     **/
    void deleteRole(Long roleId);

    /**
     * 添加角色-权限之间关系
     *
     * @param roleId
     * @param permissionIds
     */
    public void correlationPermissions(Long roleId, Long... permissionIds);

    /**
     * 移除角色-权限之间关系
     *
     * @param roleId
     * @param permissionIds
     */
    public void uncorrelationPermissions(Long roleId, Long... permissionIds);

    List<Role> getRoles();

    RoleInfoDTO getRoleAndPermissions(String id);
}
