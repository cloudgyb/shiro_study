package com.gyb.shiro.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author geng
 * 2020/10/14
 */
@Getter
@Setter
public class SysUser {
    private int id;
    private String username;
    private String password;
    private String passwordSalt;
    private boolean isLocked;
    private String roleIds;
    public SysUser(){}
    public SysUser(int id, String username, String password, String passwordSalt, boolean isLocked, String roleIds) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.passwordSalt = passwordSalt;
        this.isLocked = isLocked;
        this.roleIds = roleIds;
    }

    public SysUser(int id, String username, String dbPassword, String passwordSalt) {
        this.id = id;
        this.username = username;
        this.password = dbPassword;
        this.passwordSalt = passwordSalt;
    }
    public SysUser(String username, String dbPassword, String passwordSalt){
        this.username = username;
        this.password = dbPassword;
        this.passwordSalt = passwordSalt;
    }
    public void setIsLocked(boolean isLocked){
        this.isLocked = isLocked;
    }
    public boolean getIsLocked(){
        return isLocked;
    }
}
