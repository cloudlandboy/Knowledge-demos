package cn.clboy.demo.shiro.springboot.service.impl;


import cn.clboy.demo.shiro.springboot.dto.RoleInfoDTO;
import cn.clboy.demo.shiro.springboot.entity.Role;
import cn.clboy.demo.shiro.springboot.mapper.RoleMapper;
import cn.clboy.demo.shiro.springboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public RoleMapper getRoleMapper() {
        return roleMapper;
    }

    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    public Role createRole(Role role) {
        return roleMapper.createRole(role);
    }

    public void deleteRole(Long roleId) {
        roleMapper.deleteRole(roleId);
    }

    /**
     * 添加角色-权限之间关系
     *
     * @param roleId
     * @param permissionIds
     */
    public void correlationPermissions(Long roleId, Long... permissionIds) {
        roleMapper.correlationPermissions(roleId, permissionIds);
    }

    /**
     * 移除角色-权限之间关系
     *
     * @param roleId
     * @param permissionIds
     */
    public void uncorrelationPermissions(Long roleId, Long... permissionIds) {
        roleMapper.uncorrelationPermissions(roleId, permissionIds);
    }

    @Override
    public List<Role> getRoles() {
        return roleMapper.getRoles();
    }

    @Override
    public RoleInfoDTO getRoleAndPermissions(String id) {
        return roleMapper.getRoleAndPermissions(id);
    }
}
