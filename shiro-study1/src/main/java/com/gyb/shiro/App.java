package com.gyb.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入用户名：");
        String username = scanner.next();
        System.out.println("输入密码：");
        String password = scanner.next();
        Realm iniRealm = new IniRealm("classpath:shiro.ini");
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(iniRealm);
        //3.
        SecurityUtils.setSecurityManager(securityManager);
        Subject user = SecurityUtils.getSubject();
        boolean authenticated = user.isAuthenticated();
        if (authenticated) {
            System.out.println("User authenticated!");
        } else {
            System.out.println("User not authenticated!");
        }
        //login
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        System.out.println("开始登陆....");
        try {
            user.login(token);
            System.out.println("登录成功！");
        }catch (IncorrectCredentialsException e){
            System.out.println("用户密码错误！");
        }catch (Exception e){
            System.out.println("用户登录失败！");
        }
    }
}
