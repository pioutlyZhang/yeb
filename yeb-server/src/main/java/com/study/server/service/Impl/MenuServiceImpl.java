package com.study.server.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.server.mapper.MenuMapper;
import com.study.server.pojo.Admin;
import com.study.server.pojo.Menu;
import com.study.server.pojo.RespBean;
import com.study.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取用户菜单根据用户id
     * @return
     */
    @Override
    public List<Menu> getMenuByAdmin() {
        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = admin.getId();
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //从redis中获取数据
        List menus = (List<Menu>) valueOperations.get("menu_" + id);
//        如果redis中没有
        if(CollectionUtils.isEmpty(menus)){
//            从数据库中获取
            menus = menuMapper.getMenuByAdminId(id);
//            将数据放到redis中
            valueOperations.set("menu_" + id,menus);
        }
        return menus;
    }

    /**
     * 根据角色获取菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenuWithRole() {
        return menuMapper.getMenuWithRole();
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }

}
