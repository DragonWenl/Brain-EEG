package com.brain.controller;

import com.alibaba.fastjson.JSONArray;
import com.brain.base.result.Results;
import com.brain.model.Permission;
import com.brain.model.RoleDto;
import com.brain.service.PermissionService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("permission")
@Slf4j
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/listAllPermission", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:query')")
    @ApiOperation(value = "获取所有权限值", notes = "获取所有菜单的权限值")//描述
    public Results<JSONArray> listAllPermission() {
        return permissionService.listAllPermission();
    }

    @RequestMapping(value = "/listAllPermissionByRoleId", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:query')")
    @ApiOperation(value = "获取角色权限", notes = "根据角色Id去查询拥有的权限")//描述
    public Results<Permission> listAllPermissionByRoleId(RoleDto roleDto) {
        return permissionService.listByRoleId(roleDto.getId().intValue());
    }

    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:query')")
    @ApiOperation(value = "获取所有权限值", notes = "获取所有菜单的权限值")//描述
    public Results getMenuAll(){
        return permissionService.getMenuAll();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ApiOperation(value = "新增页面", notes = "跳转到菜单信息新增页面")//描述
    @PreAuthorize("hasAuthority('menu:add')")
    public String addPermission(Model model) {
        model.addAttribute("sysPermission",new Permission());
        return "permission/permission-add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:add')")
    @ApiOperation(value = "添加菜单", notes = "保存用户新增的菜单信息")//描述
    public Results<Permission> savePermission(@RequestBody Permission permission) {
        return permissionService.save(permission);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    @ApiOperation(value = "编辑页面", notes = "跳转到菜单信息编辑页面")//描述
    @PreAuthorize("hasAuthority('menu:edit')")
    public String editPermission(Model model, Permission permission) {
        model.addAttribute("sysPermission",permissionService.getPermissionById(permission.getId()));
        return "permission/permission-add";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @PreAuthorize("hasAuthority('menu:edit')")
    @ResponseBody
    @ApiOperation(value = "更新菜单信息", notes = "保存用户编辑完的菜单信息")//描述
    public Results updatePermission(@RequestBody  Permission permission) {
        return permissionService.updatePermission(permission);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:del')")
    @ApiOperation(value = "删除菜单", notes = "根据菜单Id去删除菜单")//描述
    public Results deletePermission(Permission permission) {
        return permissionService.delete(permission.getId());
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasAuthority('menu:query')")
    @ApiOperation(value = "获取菜单", notes = "获取用户所属角色下能显示的菜单")//描述
    public Results<Permission> getMenu(Long userId) {
        return permissionService.getMenu(userId);
    }
}
