package com.study.server.controller;


import com.study.server.pojo.Admin;
import com.study.server.pojo.RespBean;
import com.study.server.pojo.Role;
import com.study.server.service.IAdminService;
import com.study.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IRoleService roleService;

    @ApiOperation("获取操作员")
    @GetMapping("/")
    public List<Admin> getAllAdmins(String keyword){
        return adminService.getAllAdmins(keyword);
    }
    @ApiOperation("更新操作员基本信息")
    @PutMapping("/")
    public RespBean updateAdmins(@RequestBody Admin admin){
        if(adminService.updateById(admin)){
            return RespBean.success("更新成功");
        }else{
            return RespBean.error("更新失败");
        }
    }
    @ApiOperation("删除操作员")
    @DeleteMapping("/{id}")
    public RespBean deleteAdmins(@PathVariable("id") Integer id){
        if(adminService.removeById(id)){
            return RespBean.success("删除成功");
        }else{
            return RespBean.error("删除失败");
        }
    }
    @ApiOperation("获取所有角色")
    @GetMapping("/role")
    public List<Role> getRoles(){
        return roleService.list();
    }

    @ApiOperation("更新操作员角色")
    @PutMapping("/role")
    public RespBean updateAdminRoles(Integer adminId,Integer[] rids){
        return adminService.updateAdminRoles(adminId,rids);
    }
}
