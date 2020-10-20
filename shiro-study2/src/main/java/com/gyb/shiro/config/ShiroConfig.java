package com.gyb.shiro.config;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * @author geng
 * 2020/10/10
 */
@Configuration
public class ShiroConfig {
    @Bean
    public Realm iniRealm(){
        return new IniRealm("classpath:shiro.ini");
    }

    @Bean
    public Realm jdbcRealm(DataSource dataSource){
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        return jdbcRealm;
    }

    @Bean
    public RememberMeManager rememberMeManager(){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
        final SimpleCookie rememberMe = new SimpleCookie("rememberMe");
        rememberMe.setMaxAge(10*24*60*60);
        rememberMeManager.setCookie(rememberMe);
        return rememberMeManager;
    }

    @Bean
    public DefaultSecurityManager securityManager(Realm realm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        //关联SecurityManager
        filter.setSecurityManager(securityManager);
        HashMap<String, String> map = new HashMap<>();
        map.put("/static/**","anon");
        map.put("/login.html","anon");
        map.put("/signup.html","anon");
        map.put("/user/login","anon");
        map.put("/user/signup","anon");
        map.put("/noLogin","anon");
        map.put("/","user");
        map.put("/**","authc");
        map.put("/logout","logout");
        //map.put("/user/list.html","perms[user:aaaa]");
        filter.setFilterChainDefinitionMap(map);
        filter.setLoginUrl("/login.html");
        filter.setUnauthorizedUrl("/nopermission.html");
        return filter;
    }


    @Bean  // 等价于aop注解中的   <aop:aspectj-autoproxy> 开启aop注解  就会向容器注入一个DefaultAdvisorAutoProxyCreator
    @ConditionalOnMissingBean  // 当容器中没有当前类的时候注入，如果有了的话就不注入
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 优先使用cglib 完成代理
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
