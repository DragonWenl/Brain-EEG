package com.brain.controller;

import com.brain.base.result.PageTableRequest;
import com.brain.base.result.ResponseCode;
import com.brain.base.result.Results;
import com.brain.model.SysUser;
import com.brain.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/list")
    @ApiOperation(value = "分页获取用户信息", notes = "分页获取用户信息")//描述
    @ApiImplicitParam(name = "request", value = "分页查询实体类", required=false) //请求的参数含义
    @PreAuthorize("hasAnyAuthority('user:query')")
    @ResponseBody
    public Results<SysUser> getUsers(PageTableRequest pageTableRequest) {
        pageTableRequest.countOffset();
        return userService.getUsersByPageRequest(pageTableRequest.getOffset(), pageTableRequest.getLimit());
    }

    //跳转到增加用户的页面
    @GetMapping("/add")
    @ApiOperation(value = "用户新增页面", notes = "跳转到新增用户信息页面")//描述
    @PreAuthorize("hasAnyAuthority('user:add')")
    public String addUser(Model model) {
        model.addAttribute(new SysUser());
        return "user/user-add";
    }

    //跳转到欢迎页面
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    //增加用户
    @PostMapping("/add")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('user:add')")
    @ApiOperation(value = "保存用户信息", notes = "保存新增的用户信息")//描述
    public Results saveUser(SysUser user, Integer roleId) {
        SysUser sysUser;
        sysUser = userService.getUserById(user.getId());
        if (sysUser != null) {
            return Results.failure(ResponseCode.ID_REPEAT.getCode(),ResponseCode.ID_REPEAT.getMessage());
        }
        user.setCount(0L);
        //密码加密
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.saveUser(user, roleId);
    }

    //格式转换
    String pattern = "yy-MM-dd";

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat(pattern), true));
    }

    //修改用户内容
    @GetMapping("/edit")
    @ApiOperation(value = "用户编辑页面", notes = "跳转到用户信息编辑页面")//描述
    @ApiImplicitParam(name = "user", value = "用户实体类", dataType = "SysUser")
    public String addUser(Model model, SysUser user) {
        model.addAttribute(userService.getUserById(user.getId()));
        return "user/user-edit";
    }

    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation(value = "保存用户信息", notes = "保存编辑完的用户信息")//描述
    @PreAuthorize("hasAuthority('user:edit')")
    public Results<SysUser> updateUser(SysUser user, Integer roleId) {
        return userService.updateUser(user, roleId);
    }

    //删除用户
    @GetMapping("/delete")
    @ResponseBody
    @ApiOperation(value = "删除用户信息", notes = "删除已有的用户信息")//描述
    @PreAuthorize("hasAuthority('user:del')")
    public Results deleteUser(SysUser user) {
        int success = userService.deleteUser(user.getId());
        if(success > 0){
            return Results.success();
        }else{
            return Results.failure();
        }
    }

    //模糊查询
    @GetMapping("/findUserByFuzzyUsername")
    @ResponseBody
    @PreAuthorize("hasAuthority('user:query')")
    @ApiOperation(value = "模糊查询", notes = "查询用户信息")//描述
    public Results<SysUser> findUserByFuzzyUsername(PageTableRequest request, String username) {
        request.countOffset();
        return userService.getUserByFuzzyUsername(username, request.getOffset(), request.getLimit());
    }

    @PostMapping("/changePassword")
    @ApiOperation(value = "修改密码")
    @ResponseBody
    public Results<SysUser> changePassword(String username, String oldPassword, String newPassword) {
        return userService.changePassword(username, oldPassword, newPassword);
    }
}
