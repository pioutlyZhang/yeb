package com.study.server.controller;

import com.study.server.pojo.Admin;
import com.study.server.pojo.RespBean;
import com.study.server.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 个人中心
 */
@RestController
public class AdminInfoController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation("更新用户信息")
    @PutMapping("/admin/info")
    public RespBean updateAdminInfo(@RequestBody Admin admin, Authentication authentication){
        if(adminService.updateById(admin)){
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                    admin,null,authentication.getAuthorities()
            ));
            return RespBean.success("更新成功");
        }else{
            return RespBean.error("更新失败");
        }
    }
    @ApiOperation("更新密码")
    @PutMapping("/admin/pwd")
    public RespBean updateAdminPassword(@RequestBody Map<String,Object> info){
        String oldPass = (String)info.get("oldPass");
        String newPass = (String)info.get("newPass");
        Integer adminId = (Integer)info.get("adminId");
        if(adminService.updateAdminPassword(oldPass,newPass,adminId)){
            return RespBean.success("更新成功");
        }else{
            return RespBean.error("更新失败");
        }
    }
}
