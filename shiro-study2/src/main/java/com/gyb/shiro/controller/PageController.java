package com.gyb.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author geng
 * 2020/10/10
 */
@Controller
public class PageController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login.html")
    public String loginPage(@RequestHeader(value = "x-requested-with", required = false)
                                    String xRequestedWith) {
        if ("xmlhttprequest".equalsIgnoreCase(xRequestedWith)) //对于Ajax
            return "redirect:/noLogin";
        return "login";
    }
    @GetMapping("/signup.html")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/nopermission.html")
    public String nopermission() {
        return "nopermission";
    }

    @GetMapping("/noLogin")
    @ResponseBody
    public String noLoginResp() {
        return "{\"mess\":\"未登录，无访问权限，请登录！\"}";
    }
}
