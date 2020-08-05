package com.brain.service;

import com.brain.dao.PermissionDao;
import com.brain.model.LoginUser;
import com.brain.model.Permission;
import com.brain.model.SysUser;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liwenlong
 * @data 2020/8/3
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        SysUser user = userService.getUserById(Long.parseLong(id));
        if(user == null){
            throw new AuthenticationCredentialsNotFoundException("一卡通未注册，请注册后登陆！");
        }

        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(user, loginUser);

        List<Permission> permissions = permissionDao.listByUserId(user.getId());
        loginUser.setPermissions(permissions);

        return loginUser;
    }


}
