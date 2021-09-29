package com.study.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.server.pojo.AdminRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    Integer updateAdminRoles(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);
}
