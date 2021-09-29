package com.study.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据用户id查询菜单
     * @param id
     * @return
     */
    List<Menu> getMenuByAdminId(Integer id);

    List<Menu> getMenuWithRole();

    List<Menu> getAllMenus();
}
