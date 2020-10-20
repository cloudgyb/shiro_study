package com.gyb.shiro.entity;

import lombok.Data;

/**
 * @author geng
 * 2020/10/11
 */
@Data
public class User {
    private int id;
    private String userName;
    private String password;
    private String PasswordSalt;
}
