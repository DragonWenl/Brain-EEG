package com.brain.dao;

import com.brain.model.Permission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PermissionDao {
    @Select("select * from permission")
    List<Permission> findAll();

    @Select("select p.* from permission p inner join role_permission rp on p.id = rp.permissionId where rp.roleId = #{roleId} order by p.sort")
    List<Permission> listByRoleId(Integer roleId);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into permission(parentId, name, css, href, type, permission, sort) values(#{parentId}, #{name}, #{css}, #{href}, #{type}, #{permission}, #{sort})")
    int save(Permission e);

    @Select("select * from permission t where t.id = #{id}")
    Permission getPermissionById(Integer id);

    int update(Permission e);

    @Delete("delete from permission where id = #{id}")
    int deleteById(Integer id);

    @Delete("delete from permission where parentId = #{parentId}")
    int deleteByParentId(Integer parentId);

    @Select("select p.* from role_user ru " +
            "inner join role_permission rp on rp.roleId = ru.roleId " +
            "left join permission p on rp.permissionId = p.id " +
            "where ru.userId = #{userId}")
    List<Permission> findMenuById(@Param("userId") Long userId);


    @Select("SELECT DISTINCT sp.*  " +
            "FROM role_user sru " +
            "INNER JOIN role_permission srp ON srp.roleId = sru.roleId " +
            "LEFT JOIN permission sp ON srp.permissionId = sp.id " +
            "WHERE sru.userId = #{userId}")
    List<Permission> listByUserId(@Param("userId") Long userId);

}
