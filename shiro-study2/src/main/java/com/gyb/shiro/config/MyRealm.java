package com.gyb.shiro.config;

import com.gyb.shiro.dao.SysUserDao;
import com.gyb.shiro.entity.SysUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author geng
 * 2020/10/11
 */
@Component
@Primary
public class MyRealm extends AuthorizingRealm {
    @Resource
    private SysUserDao sysUserDao;

    public MyRealm() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setHashAlgorithmName("md5");
        this.setCredentialsMatcher(credentialsMatcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String userName = (String)principalCollection.iterator().next();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = sysUserDao.getUserRolesByUsername(userName);
        authorizationInfo.setRoles(roles);
        Set<String> permissions = sysUserDao.getUserPermissionsByUsername(userName);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        Object userName = authenticationToken.getPrincipal();
        SysUser user = sysUserDao.getUserByUserName((String) userName);
        if(user != null) {
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
            authenticationInfo.setCredentials(user.getPassword());
            authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(user.getPasswordSalt()));
            SimplePrincipalCollection principal = new SimplePrincipalCollection();
            principal.add(authenticationToken.getPrincipal(),getName());
            authenticationInfo.setPrincipals(principal);
            return authenticationInfo;
        }
        return null;
    }

    @Override
    public String getName() {
        return "myRealm";
    }
}
