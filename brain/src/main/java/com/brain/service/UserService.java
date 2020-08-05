package com.brain.service;

import com.brain.base.result.Results;
import com.brain.dao.UserDao;
import com.brain.model.RoleUser;
import com.brain.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleUserService roleUserService;

    public SysUser getUserByName(String username) {
        return userDao.getUser(username);
    }

    public SysUser getUserById(Long id){
        return userDao.getUserById(id);
    }

    public Results<SysUser> getUsersByPageRequest(Integer offset, Integer limit) {
        //返回 cout users
        return Results.success(userDao.countAllUsers().intValue(), userDao.getAllUsersByPage(offset, limit));
    }

    //向数据库中增加用户
    public Results saveUser(SysUser user, Integer roleId) {
        if (roleId != null) {
            user.setStatus(roleId);
            userDao.save(user);
            RoleUser roleUser = new RoleUser();
            roleUser.setRoleId(roleId);
            roleUser.setUserId(user.getId());
            roleUserService.save(roleUser);
            return Results.success();
        }else return Results.failure();
    }

    //修改操作
    public Results<SysUser> updateUser(SysUser user, Integer roleId) {
        if(roleId != null){
            userDao.updateUser(user);
            RoleUser roleUser = new RoleUser();
            roleUser.setUserId(user.getId());
            roleUser.setRoleId(roleId);
            if(roleUserService.getRoleUserByUserId(user.getId()) != null){
                roleUserService.updateRoleUser(roleUser);
            }else{
                roleUserService.save(roleUser);
            }
            return Results.success();
        }else{
            return Results.failure();
        }
    }

    //删除操作
    public int deleteUser(Long id) {
        roleUserService.deleteRoleUserByUserId(id.intValue());
        return userDao.deleteUser(id.intValue());
    }

    public Results<SysUser> getUserByFuzzyUsername(String username, Integer offset, Integer limit) {
        return Results.success(userDao.getUserByFuzzyUsername(username).intValue(), userDao.getUserByFuzzyUsernameByPage(username, offset, limit));
    }

    public Results<SysUser> changePassword(String username, String oldPassword, String newPassword) {
        SysUser u = userDao.getUser(username);
        if (u == null) {
            return Results.failure(1,"用户不存在");
        }
        if (!new BCryptPasswordEncoder().encode(oldPassword).equals(u.getPassword())) {
            return Results.failure(1,"旧密码错误");
        }
        userDao.changePassword(u.getId(), new BCryptPasswordEncoder().encode(newPassword));
        return Results.success();
    }
}
