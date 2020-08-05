package com.brain.controller;

import com.brain.base.result.PageTableRequest;
import com.brain.base.result.Results;
import com.brain.model.Role;
import com.brain.model.RoleDto;
import com.brain.service.RoleService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author liwenlong
 * @data 2020/8/2
 */
@Controller
@RequestMapping("role")
@Slf4j
public class RoleController {

    @Autowired
    private RoleService roleService;
    private static Logger log = LoggerFactory.getLogger(RoleController.class);


    @GetMapping("/all")
    @ResponseBody
    @ApiOperation(value = "获取所有角色", notes = "获取所有角色信息")//描述
    public Results<Role> getRoles() {
        log.info("RoleController.getRoles()" );
        return roleService.getAllRoles();
    }

    @GetMapping("/list")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:query')")
    @ApiOperation(value = "分页获取角色", notes = "用户分页获取角色信息")//描述
    public Results list(PageTableRequest request) {
        log.info("RoleController.list(): param ( request = " + request +" )");
        request.countOffset();
        return roleService.getAllRolesByPage(request.getOffset(), request.getLimit());
    }

    @GetMapping(value = "/add")
    @PreAuthorize("hasAuthority('role:add')")
    @ApiOperation(value = "新增角色信息页面", notes = "跳转到角色信息新增页面")//描述
    public String addRole(Model model) {
        model.addAttribute("Role",new Role());
        return "role/role-add";
    }

    @PostMapping(value = "/add")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:add')")
    @ApiOperation(value = "保存角色信息", notes = "保存新增的角色信息")//描述
    @ApiImplicitParam(name = "roleDto",value = "角色信息实体类", required = true,dataType = "RoleDto")
    public Results saveRole(@RequestBody RoleDto roleDto) {
        return roleService.save(roleDto);
    }

    @GetMapping("/findRoleByFuzzyRoleName")
    @ResponseBody
    @PreAuthorize("hasAuthority('role:query')")
    @ApiOperation(value = "模糊查询角色信息", notes = "模糊搜索查询角色信息")//描述
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName",value = "模糊搜索的角色名", required = true),
    })
    public Results findRoleByFuzzyRoleName(PageTableRequest requests, String roleName) {
        requests.countOffset();
        return roleService.getRoleByFuzzyRoleNamePage(roleName,requests.getOffset(),requests.getLimit());
    }

    @GetMapping(value = "/edit")
    @PreAuthorize("hasAuthority('role:edit')")
    @ApiOperation(value = "编辑角色信息页面", notes = "跳转到角色信息编辑页面")//描述
    public String editRole(Model model, Role role) {
        model.addAttribute("sysRole",roleService.getRoleById(role.getId().intValue()));
        return "role/role-edit";
    }

    @PostMapping(value = "/edit")
    @PreAuthorize("hasAuthority('role:edit')")
    @ResponseBody
    @ApiOperation(value = "保存角色信息", notes = "保存被编辑的角色信息")//描述
    public Results updateRole(@RequestBody RoleDto roleDto) {
        return roleService.update(roleDto);
    }

    @GetMapping(value = "/delete")
    @PreAuthorize("hasAuthority('role:del')")
    @ApiOperation(value = "删除角色信息", notes = "删除角色信息")//描述
    @ResponseBody
    public Results<Role> deleteRole(RoleDto roleDto) {
        return roleService.delete(roleDto.getId().intValue());
    }
}
