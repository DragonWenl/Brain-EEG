package com.brain.dao;

import com.brain.model.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author liwenlong
 * @data 2020/8/1
 */
@Mapper
public interface UserDao {

    @Select("select * from user t where t.username = #{username}")
    SysUser getUser(String username);

    @Select("select * from user t order by t.id limit #{startPosition} , #{limit}")
    List<SysUser> getAllUsersByPage(@Param("startPosition")Integer startPosition, @Param("limit") Integer limit);

    @Select("select count(*) from user t")
    Long countAllUsers();

    @Insert("insert into user(id,username, password,headImgUrl,telephone, email, birthday, sex, status, stage, count, createTime, updateTime) values(#{id},#{username}, #{password}, #{headImgUrl}, #{telephone}, #{email}, #{birthday}, #{sex}, #{status}, #{stage},#{count},now(), now())")
    int save(SysUser user);

    @Select("select * from user t where t.id = #{id}")
    SysUser getUserById(Long id);

    int updateUser(SysUser sysUser);

    @Delete("delete from user where id = #{userId}")
    int deleteUser(int userId);

    @Select("select count(*) from user t where t.username like '%${username}%'")
    Long getUserByFuzzyUsername(@Param("username") String username);

    @Select("select * from user t where t.username like '%${username}%' limit #{startPosition} , #{limit}")
    List<SysUser> getUserByFuzzyUsernameByPage(@Param("username") String username, @Param("startPosition") Integer startPosition, @Param("limit") Integer limit);

    @Update("update user set password = #{password} where id = #{id}")
    int changePassword(@Param("id") Long id, @Param("password") String password);
}

