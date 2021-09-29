package com.study.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.server.pojo.Admin;
import com.study.server.pojo.RespBean;
import com.study.server.pojo.Role;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
public interface IAdminService extends IService<Admin> {

    RespBean login(String username, String password,String code, HttpServletRequest request);

    Admin getAdminByUsername(String username);

    List<Role> getRolesByAdminId(Integer id);

    List<Admin> getAllAdmins(String keyword);

    RespBean updateAdminRoles(Integer adminId, Integer[] rids);

    boolean updateAdminPassword(String oldPass, String newPass, Integer adminId);
}
