package com.study.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.server.pojo.Menu;
import com.study.server.pojo.RespBean;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getMenuByAdmin();

    List<Menu> getMenuWithRole();

    List<Menu> getAllMenus();

}
