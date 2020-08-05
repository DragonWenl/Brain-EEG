package com.brain.service;


import com.brain.base.result.Results;
import com.brain.dao.RoleUserDao;
import com.brain.model.RoleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleUserService {
    @Autowired
    private RoleUserDao roleUserDao;

    public Results getRoleUserByUserId(Long userId) {
        RoleUser roleUser = roleUserDao.getRoleUserByUserId(userId);
        if(roleUser != null){
            return Results.success(roleUser);
        }else{
            return Results.success();
        }
    }

    public void save(RoleUser roleUser) {
        roleUserDao.save(roleUser);
    }


    public void updateRoleUser(RoleUser roleUser) {
        roleUserDao.updateRoleUser(roleUser);
    }

    public void deleteRoleUserByUserId(int userId) {
        roleUserDao.deleteRoleUserByUserId(userId);
    }
}
