package com.study.server.controller;

import com.study.server.pojo.Admin;
import com.study.server.pojo.AdminLoginParam;
import com.study.server.pojo.RespBean;
import com.study.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * 登录controller
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService adminService;
    @ApiOperation("登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
        return adminService.login(adminLoginParam.getUsername(),
                adminLoginParam.getPassword(),adminLoginParam.getCode(),request);


    }

    @ApiOperation("返回用户信息")
    @GetMapping("/admin/info")
    public RespBean getAdminInfo(Principal principal){
        if(principal==null){
            return RespBean.error("未找到用户");
        }
        String username = principal.getName();
        Admin admin = adminService.getAdminByUsername(username);
        admin.setPassword(null);
        admin.setRoles(adminService.getRolesByAdminId(admin.getId()));
        return RespBean.success("成功找到用户",admin);

    }


    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public RespBean logout(AdminLoginParam adminLoginParam, HttpRequest request){
//        前端删除保存token信息的请求头
        return RespBean.success("注销成功");
    }

}
