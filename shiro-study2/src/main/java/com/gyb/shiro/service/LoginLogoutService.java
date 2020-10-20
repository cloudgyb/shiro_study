package com.gyb.shiro.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

/**
 * @author geng
 * 2020/10/10
 */
@Service
@Slf4j
public class LoginLogoutService {
    public void login(String username,String password,boolean rememberMe){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(rememberMe);
        try {
            subject.login(token);
        }catch (IncorrectCredentialsException e){
            log.info(String.format("用户%s密码错误，登录失败！", username));
        }catch (UnknownAccountException e){
            log.info(String.format("用户%s不存在，登录失败！", username));
        }catch (Exception e){
            log.info(String.format("用户%s登录失败！", username),e.getMessage());
        }
    }
    public void logout(){
        Subject user = SecurityUtils.getSubject();
        user.logout();
    }
}
