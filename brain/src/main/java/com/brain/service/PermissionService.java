package com.brain.service;

import com.alibaba.fastjson.JSONArray;
import com.brain.base.result.Results;
import com.brain.dao.PermissionDao;
import com.brain.model.Permission;
import com.brain.util.TreeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    public Results<JSONArray> listAllPermission() {
        List datas = permissionDao.findAll();
        JSONArray array = new JSONArray();
        TreeUtils.setPermissionsTree(0, datas, array);
        return Results.success(array);
    }

    public Results<Permission> listByRoleId(Integer roleId) {
        List<Permission> datas = permissionDao.listByRoleId(roleId);
        return Results.success(0, datas);
    }

    public Results<Permission> getMenuAll() {
        return Results.success(0, permissionDao.findAll());
    }

    public Results save(Permission Permission) {
        return (permissionDao.save(Permission) > 0) ? Results.success() : Results.failure();
    }

    public Permission getPermissionById(Integer id) {
        return permissionDao.getPermissionById(id);
    }

    public Results updatePermission(Permission sysPermission) {
        return (permissionDao.update(sysPermission) > 0) ? Results.success() : Results.failure();
    }

    public Results delete(Integer id) {
        permissionDao.deleteById(id);
        permissionDao.deleteByParentId(id);
        return Results.success();
    }

    public Results getMenu(Long userId) {
        List<Permission> datas = permissionDao.findMenuById(userId);
        datas = datas.stream().filter(p -> p.getType().equals(1)).collect(Collectors.toList());
        JSONArray array = new JSONArray();
        TreeUtils.setPermissionsTree(0, datas, array);
        return Results.success(array);
    }
}
