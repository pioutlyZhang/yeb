package com.study.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.server.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    Integer insertRocode(@Param("rid") Integer rid, @Param("mids") Integer[] mids);


}
