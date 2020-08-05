package com.brain.dao;

import com.brain.model.RoleUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleUserDao {

    @Insert("insert into role_user(userId, roleId) values(#{userId}, #{roleId})")
    void save(RoleUser RoleUser);

    @Select("select * from role_user t where t.userId = #{userId}")
    RoleUser getRoleUserByUserId(Long userId);

    int updateRoleUser(RoleUser roleUser);

    @Delete("delete from role_user where userId = #{userId}")
    int deleteRoleUserByUserId(int userId);

    @Select("select * from role_user t where t.roleId = #{roleId}")
    List<RoleUser> listAllRoleUserByRoleId(Integer roleId);
}
