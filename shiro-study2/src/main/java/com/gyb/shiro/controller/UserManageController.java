package com.gyb.shiro.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author geng
 * 2020/10/15
 */
@Controller
public class UserManageController {

    @GetMapping("/user/list.html")
    @RequiresPermissions("user:list")
    public String viewList(){
        return "user/list";
    }
}
