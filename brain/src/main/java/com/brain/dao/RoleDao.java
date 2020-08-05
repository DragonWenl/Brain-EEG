package com.brain.dao;

import com.brain.model.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleDao {

    @Select("select * from role")
    List<Role> getAllRoles();

    @Select("select count(*) from role")
    Long countAllRoles();

    @Select("select * from role t limit #{startPosition}, #{limit}")
    List<Role> getAllRolesByPage(@Param("startPosition")Integer startPosition, @Param("limit")Integer limit);

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into sys_role(name, description, createTime, updateTime) values(#{name}, #{description}, now(), now())")
    int save(Role role);

    int saveRole(Role role);

    @Select("select * from role where id = #{id}")
    Role getById(Integer id);

    int update(Role role);

    @Delete("delete from role where id = #{id}")
    int delete(Integer id);

    @Select("select count(*) from role t where t.name like '%${roleName}%'")
    Long countRoleByFuzzyRoleName(@Param("roleName") String roleName);

    @Select("select * from role t where t.name like '%${roleName}%' limit #{startPosition},#{limit}")
    List<Role> getRoleByFuzzyRoleNamePage(@Param("roleName") String roleName,@Param("startPosition")Integer startPosition,@Param("limit")Integer limit);

}
