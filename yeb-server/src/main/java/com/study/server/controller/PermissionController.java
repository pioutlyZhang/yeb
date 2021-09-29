package com.study.server.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.study.server.pojo.*;
import com.study.server.service.IMenuRoleService;
import com.study.server.service.IMenuService;
import com.study.server.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限组
 */
@RestController
@RequestMapping("system/basic/premiss")
public class PermissionController {
    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IMenuRoleService menuRoleService;


    @ApiOperation("获取角色信息")
    @GetMapping("/")
    public List<Role> getAllRole(){
        return roleService.list();
    }

    @ApiOperation("添加角色信息")
    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role){
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_" + role.getName());
        }
        if (roleService.save(role)) {
            return RespBean.success("保存成功");
        }else{
            return RespBean.error("保存失败");
        }
    }
    @ApiOperation("删除角色信息")
    @DeleteMapping("/role/{rid}")
    public RespBean deleteRole(@PathVariable("rid") Integer rid){
        if (roleService.removeById(rid)) {
            return RespBean.success("删除成功");
        }else{
            return RespBean.error("删除失败");
        }
    }
    @ApiOperation("获取所有菜单信息")
    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();

    }
    @ApiOperation("根据id获取菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByRid(@PathVariable("rid") Integer rid){
        return menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid",rid)).stream()
                .map(MenuRole::getMid).collect(Collectors.toList());
    }
    @ApiOperation("更新角色菜单")
    @PutMapping("/")
    public RespBean updateRoleMenu(Integer rid,Integer[] mids){
        return menuRoleService.updateRoleMenu(rid,mids);
    }
}
