package com.gyb.shiro.controller;

import com.gyb.shiro.service.LoginLogoutService;
import com.gyb.shiro.service.SignupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author geng
 * 2020/10/10
 */
@Controller
public class LoginLogoutController {
    @Resource
    private LoginLogoutService loginLogoutService;
    @Resource
    private SignupService signupService;

    @PostMapping("/user/login")
    public String login(String username,String password,boolean rememberMe){
        loginLogoutService.login(username,password,rememberMe);
        return "redirect:/";
    }
    @RequestMapping("/user/logout")
    public String logout(){
        loginLogoutService.logout();
        return "redirect:/";
    }

    @PostMapping("/user/signup")
    public String signup(String username,String password){
        signupService.signup(username,password);
        return "redirect:/login.html";
    }
}
