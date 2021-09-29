package com.study.server.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.server.mapper.MenuRoleMapper;
import com.study.server.pojo.MenuRole;
import com.study.server.pojo.RespBean;
import com.study.server.service.IMenuRoleService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */

/**
 * 更新角色菜单
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {
    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Override
    @Transactional
    public RespBean updateRoleMenu(Integer rid, Integer[] mids) {
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));
        if(mids==null||mids.length==0){
            return RespBean.success("更新成功");
        }
        Integer integer = menuRoleMapper.insertRocode(rid, mids);
        if(integer==mids.length){
            return RespBean.success("更新成功");
        }else{
            return RespBean.error("更新失败");
        }

    }
}
