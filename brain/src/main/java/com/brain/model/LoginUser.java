package com.brain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liwenlong
 * @data 2020/8/3
 */
@Data
public class LoginUser extends SysUser implements UserDetails {

    private List<Permission> permissions;

    //拥有的权限
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.parallelStream().filter(p -> !StringUtils.isEmpty(p.getPermission()))
                .map(p ->new SimpleGrantedAuthority(p.getPermission())).collect(Collectors.toSet());
    }

    // 账户是否未过期
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    //账户是否锁定
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    // 密码是否未过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //是否激活
    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
