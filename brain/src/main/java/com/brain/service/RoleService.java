package com.brain.service;

import com.brain.base.result.ResponseCode;
import com.brain.base.result.Results;
import com.brain.dao.RoleDao;
import com.brain.dao.RolePermissionDao;
import com.brain.dao.RoleUserDao;
import com.brain.model.Role;
import com.brain.model.RoleDto;
import com.brain.model.RoleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
public class RoleService {
	@Autowired
	RoleDao roleDao;
	@Autowired
	RolePermissionDao rolePermissionDao;
	@Autowired
	RoleUserDao roleUserDao;
	public Results<Role> getAllRoles() {
		return Results.success(50,roleDao.getAllRoles());
	}

	public Results<Role> getAllRolesByPage(Integer offset, Integer limit) {
		return Results.success(roleDao.countAllRoles().intValue(), roleDao.getAllRolesByPage(offset, limit));
	}

	public Results<Role> getRoleByFuzzyRoleNamePage(String roleName, Integer startPosition, Integer limit) {
		return Results.success(roleDao.countRoleByFuzzyRoleName(roleName).intValue(), roleDao.getRoleByFuzzyRoleNamePage(roleName, startPosition, limit));
	}

	public Results<Role> save(RoleDto roleDto) {
		//1、先保存角色"
		roleDao.saveRole(roleDto);
		List<Long> permissionIds = roleDto.getPermissionIds();
		//移除0,permission id是从1开始
		permissionIds.remove(0L);
		//2、保存角色对应的所有权限
		if (!CollectionUtils.isEmpty(permissionIds)) {
//			rolePermissionDao.save(roleDto.getId(), permissionIds);
			rolePermissionDao.save(roleDto.getId().intValue(), permissionIds);
		}
		return Results.success();
	}

	public Role getRoleById(Integer id) {
		return roleDao.getById(id);
	}

	public Results update(RoleDto roleDto) {
		List<Long> permissionIds = roleDto.getPermissionIds();
		permissionIds.remove(0L);
		//1、更新角色权限之前要删除该角色之前的所有权限
		rolePermissionDao.deleteRolePermission(roleDto.getId().intValue());

		//2、判断该角色是否有赋予权限值，有就添加"
		if (!CollectionUtils.isEmpty(permissionIds)) {
			rolePermissionDao.save(roleDto.getId().intValue(), permissionIds);
		}

		//3、更新角色表
		int countData = roleDao.update(roleDto);

		if(countData > 0){
			return Results.success();
		}else{
			return Results.failure();
		}
	}

	public Results delete(Integer id) {
		List<RoleUser> datas = roleUserDao.listAllRoleUserByRoleId(id);
		if(datas.size() <= 0){
			roleDao.delete(id);
			return Results.success();
		}
		return Results.failure(ResponseCode.ID_REPEAT.USER_ROLE_NO_CLEAR.getCode(),ResponseCode.ID_REPEAT.USER_ROLE_NO_CLEAR.getMessage());
	}
}
