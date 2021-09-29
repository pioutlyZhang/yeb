package com.study.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.server.pojo.MenuRole;
import com.study.server.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
public interface IMenuRoleService extends IService<MenuRole> {

    RespBean updateRoleMenu(Integer rid, Integer[] mids);
}
