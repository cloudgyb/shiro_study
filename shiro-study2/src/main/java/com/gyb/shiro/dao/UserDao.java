package com.gyb.shiro.dao;

import com.gyb.shiro.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author geng
 * 2020/10/11
 */
@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    public UserDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public User getUserByUserName(String userName){
        String sql = "select id as id,username as username,password as password,password_salt as passwordSalt from users where username=?";
        List<User> users = jdbcTemplate.query(sql, new String[]{userName}, new BeanPropertyRowMapper<>(User.class));
        return !users.isEmpty() ?users.get(0):null;
    }


}
