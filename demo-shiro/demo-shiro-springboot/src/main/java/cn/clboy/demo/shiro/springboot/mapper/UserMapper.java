package cn.clboy.demo.shiro.springboot.mapper;

import cn.clboy.demo.shiro.springboot.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

/**
 * @Author clboy
 * @Date 2021/2/1 上午10:30
 * @Since 1.0.0
 */
public interface UserMapper {

    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    @Insert("INSERT INTO sys_users(username, password, salt, locked) VALUES(#{username},#{password},#{salt},#{locked})")
    void createUser(User user);

    @Update("UPDATE sys_users SET username=#{username}, password=#{password}, salt=#{salt}, locked=#{locked} WHERE id=#{id}")
    void updateUser(User user);

    @Delete("delete from user where id=#{value}")
    void deleteUser(Long userId);

    @Insert({"<script>",
            "INSERT INTO sys_users_roles(user_id, role_id) VALUES",
            "<foreach item='roleId' collection='roleIds' open='' separator=',' close=''>",
            "(#{userId},#{roleId})",
            "</foreach>",
            "</script>"})
    void correlationRoles(@Param("userId") Long userId, @Param("roleIds") Long... roleIds);

    @Delete({
            "<script>",
            "DELETE FROM sys_users_roles WHERE user_id=#{userId} AND role_id IN",
            "<foreach item='roleId' collection='roleIds' open='(' separator=',' close=')'>",
            "#{roleId}",
            "</foreach>",
            "</script>"})
    void uncorrelationRoles(@Param("userId") Long userId, @Param("roleIds") Long... roleIds);

    @Select("SELECT id, username, password, salt, locked FROM sys_users WHERE id=#{value}")
    User findOne(Long userId);

    @Select("SELECT id, username, password, salt, locked FROM sys_users WHERE username=#{value}")
    User findByUsername(String username);

    @Select("SELECT role FROM sys_users u, sys_roles r,sys_users_roles ur WHERE u.username=#{value} AND u.id=ur.user_id AND r.id=ur.role_id")
    Set<String> findRoles(String username);

    @Select("SELECT permission FROM sys_users u, sys_roles r, sys_permissions p, sys_users_roles ur, sys_roles_permissions rp WHERE u.username=#{value} AND u.id=ur.user_id AND r.id=ur.role_id AND r.id=rp.role_id AND p.id=rp.permission_id")
    Set<String> findPermissions(String username);

    @Select("SELECT id,username,locked FROM sys_users")
    List<User> findUsers();

    @Select("SELECT COUNT(id) FROM sys_users WHERE username=#{value} LIMIT 1")
    int existsUserByName(String username);
}