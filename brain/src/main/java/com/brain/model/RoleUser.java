package com.brain.model;

import lombok.Data;

/**
 * @author liwenlong
 * @data 2020/8/1
 */
@Data
public class RoleUser {
    private Long userId;
    private Integer roleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
