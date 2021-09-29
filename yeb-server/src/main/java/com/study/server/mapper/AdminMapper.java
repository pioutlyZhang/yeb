package com.study.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.server.pojo.Admin;
import com.study.server.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 获取用户的角色
     * @param id
     * @return
     */
    List<Role> getRolesByAdminId(Integer id);

    /**
     * 根据关键字获取所有的用户，但不包含当前已登录的用户
     * @param id
     * @param keyword
     * @return
     */
    List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keyword") String keyword);
}
