package com.gyb.shiro.service;

import com.gyb.shiro.dao.SysUserDao;
import com.gyb.shiro.entity.SysUser;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @author geng
 * 2020/10/14
 */
@Service
public class SignupService {
    @Resource
    private SysUserDao sysUserDao;

    public void signup(String username,String password){
        String passwordSalt = generateRandomString();
        Md5Hash md5Hash = new Md5Hash(password, passwordSalt, 2);
        String dbPassword = md5Hash.toString();
        SysUser sysUser = new SysUser(username, dbPassword, passwordSalt);
        sysUser.setRoleIds("1,");//默认角色id为1
        sysUser.setIsLocked(false);
        sysUserDao.addUser(sysUser);
    }

    private String generateRandomString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-","");
    }

    public static void main(String[] args) {
        new SignupService().generateRandomString();
    }
}
