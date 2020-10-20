package com.gyb.shiro.dao;

import com.gyb.shiro.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author geng
 * 2020/10/14
 */
@Slf4j
@Repository
public class SysUserDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public void addUser(SysUser sysUser){
        String sql = "insert into tb_user" +
                "(username,password,password_salt,role_ids,is_locked) " +
                "value(?,?,?,?,?)";
        int update = jdbcTemplate.update(sql, sysUser.getUsername(), sysUser.getPassword(),
                sysUser.getPasswordSalt(), sysUser.getRoleIds(), sysUser.getIsLocked());
        log.info("增加"+update+"个用户！");
    }

    public SysUser getUserByUserName(String userName){
        String sql = "select " +
                "id as id,username as username,password as password," +
                "password_salt as passwordSalt,role_ids as roleIds,is_locked as isLocked " +
                "from tb_user where username=?";
        List<SysUser> users = jdbcTemplate.query(sql, new String[]{userName}, new BeanPropertyRowMapper<>(SysUser.class));
        return !users.isEmpty() ?users.get(0):null;
    }
    public String getUserRoleIdsByUsername(String username){
        String queryRoleIdsSql = "select role_ids from tb_user where username=?";
        List<String> roleIdss = jdbcTemplate.queryForList(queryRoleIdsSql, new String[]{username}, String.class);
        if(!roleIdss.isEmpty()) {
            String s = roleIdss.get(0);
            return cleanIds(s);
        }
        return null;
    }
    public Set<String> getUserRolesByUsername(String username){
//        String queryRoleIdsSql = "select role_ids from tb_user where username=?";
//        List<String> roleIdss = jdbcTemplate.queryForList(queryRoleIdsSql, new String[]{username}, String.class);
        String roleIdsStr = getUserRoleIdsByUsername(username);
        if(roleIdsStr != null && !"".equals(roleIdsStr)){
            String queryRoleNameSql = "select role_name from tb_role where FIND_IN_SET(id,?)";
            List<String> roleNames = jdbcTemplate.queryForList(queryRoleNameSql, new String[]{roleIdsStr}, String.class);
            return new HashSet<>(roleNames);
        }
        return null;
    }
    public Set<String> getUserPermissionsByUsername(String username){
        String permissionIds = getUserPermissionIdsByUsername(username);
        if(permissionIds!=null){
            String sql ="select permission_code from tb_permission where FIND_IN_SET(id,?)";
            List<String> permissions = jdbcTemplate.queryForList(sql,
                    new String[]{permissionIds}, String.class);
            return new HashSet<>(permissions);
        }
        return null;
    }
    public String getUserPermissionIdsByUsername(String username){
        //1,先获取角色ids，例如 1,2,3
        String roleIdsStr = getUserRoleIdsByUsername(username);
        if(roleIdsStr != null && !"".equals(roleIdsStr)) {
            String queryPermissionsIdsSql = "select permission_ids from tb_role where FIND_IN_SET(id,?)";
            List<String> permissionIdss = jdbcTemplate.queryForList(queryPermissionsIdsSql,
                    new String[]{roleIdsStr}, String.class);
            HashSet<String> idsSet = new HashSet<>();
            for (String idss : permissionIdss) {
                if(idss == null || "".equals(idss))
                    continue;
                String[] split = idss.split(",");
                for (String s : split) {
                    if(s.equals(""))
                        continue;
                    idsSet.add(s);
                }
            }
            StringBuilder sb = new StringBuilder();
            for (String s : idsSet) {
                sb.append(s).append(',');
            }
            if(sb.length() == 0)
                return null;
            return sb.substring(0,sb.length()-1);
        }
        return null;
    }

    /**
     * 在数据库中存储的role_ids字符串，例如(1,2,3,)可能存在末尾的‘,’号，清除它，然后在拼接成正确的
     */
    private String cleanIds(String s) {
        if(s == null || "".equals(s))
            return "";
        String[] ids = s.split(",");
        StringBuilder idsStr = new StringBuilder();
        for (String id : ids) {
            if(id.equals(""))
                continue;
            idsStr.append(id).append(',');
        }
        return idsStr.substring(0,idsStr.length()-1);
    }

}
