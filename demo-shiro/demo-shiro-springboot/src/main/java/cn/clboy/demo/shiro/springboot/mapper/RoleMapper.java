package cn.clboy.demo.shiro.springboot.mapper;


import cn.clboy.demo.shiro.springboot.dto.RoleInfoDTO;
import cn.clboy.demo.shiro.springboot.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author clboy
 * @Date 2021/2/1 上午10:30
 * @Since 1.0.0
 */
public interface RoleMapper {

    Role createRole(Role role);

    void deleteRole(Long roleId);

    void correlationPermissions(Long roleId, Long... permissionIds);

    void uncorrelationPermissions(Long roleId, Long... permissionIds);

    @Select("SELECT id,role,description,available FROM sys_roles")
    List<Role> getRoles();

    @Select("SELECT id,role,description,available FROM sys_roles WHERE id=#{id}")
    @Results({@Result(column = "id", property = "permissions", many = @Many(select = "findPermissionsByRoleId"))})
    RoleInfoDTO getRoleAndPermissions(String id);

    @Select("SELECT description FROM sys_roles_permissions rp,sys_permissions p where rp.permission_id=p.id AND rp.role_id=#{rid}")
    List<String> findPermissionsByRoleId(String rid);
}