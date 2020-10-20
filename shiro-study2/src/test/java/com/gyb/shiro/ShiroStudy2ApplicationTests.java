package com.gyb.shiro;

import com.gyb.shiro.dao.SysUserDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Set;

@SpringBootTest
class ShiroStudy2ApplicationTests {
    @Resource
    SysUserDao sysUserDao;

    @Test
    void contextLoads() {
    }

    @Test
    void getUserRoleIds(){
        String geng = sysUserDao.getUserRoleIdsByUsername("geng");
        Assert.isTrue("1,2".equals(geng),"测试通过！");
        System.out.println(geng);
    }
    @Test
    void getUserRoleNames(){
        Set<String> geng = sysUserDao.getUserRolesByUsername("geng");
        System.out.println(geng);
    }

    @Test
    void getUserPermissions(){
        Set<String> geng = sysUserDao.getUserPermissionsByUsername("geng");
        System.out.println(geng);
    }

    @Test
    void getUserPermissionIds(){
        String geng = sysUserDao.getUserPermissionIdsByUsername("geng");
        Assert.isTrue("1,2,3,4".equals(geng),"测试通过！");
        System.out.println(geng);
    }

}
