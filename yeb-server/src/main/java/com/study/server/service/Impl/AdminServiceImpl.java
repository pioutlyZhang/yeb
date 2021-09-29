package com.study.server.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.server.config.security.JwtTokenUtil;
import com.study.server.mapper.AdminMapper;
import com.study.server.mapper.AdminRoleMapper;
import com.study.server.pojo.Admin;
import com.study.server.pojo.AdminRole;
import com.study.server.pojo.RespBean;
import com.study.server.pojo.Role;
import com.study.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pioutly
 * @since 2021-09-15
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    /**
     * 登录返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code,HttpServletRequest request) {
        HttpSession session = request.getSession();
        String captcha = (String) session.getAttribute("captcha");
        if(code==null||!code.equalsIgnoreCase(captcha)){
            return RespBean.error("验证码输入错误");
        }
        //登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if(userDetails==null||!passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名不存在或密码错误");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error("账户已被禁用");
        }
//        获取security对象
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails,
                        null,userDetails.getAuthorities());
//        放到SecurityContext中
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);

        Map<String,String> tokenMap = new HashMap<>();

        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);

        return RespBean.success("登陆成功",tokenMap);


    }

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username));
    }

    /**
     * 根据用户id查询角色列表
     * @param id
     * @return
     */
    @Override
    public List<Role> getRolesByAdminId(Integer id) {
        return adminMapper.getRolesByAdminId(id);
    }

    @Override
    public List<Admin> getAllAdmins(String keyword) {
        Admin admin = (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return adminMapper.getAllAdmins(admin.getId(),keyword);
    }

    /**
     * 更新操作员角色
     * @param adminId
     * @param rids
     * @return
     */
    @Transactional
    @Override
    public RespBean updateAdminRoles(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));
        if(rids==null||rids.length==0){
            return RespBean.success("删除成功");
        }
        Integer i = adminRoleMapper.updateAdminRoles(adminId,rids);
        if(i==rids.length){
            return RespBean.success("更新成功");
        }else{
            return RespBean.success("更新出现问题");
        }
    }

    @Override
    public boolean updateAdminPassword(String oldPass, String newPass, Integer adminId) {
        Admin admin = adminMapper.selectById(adminId);
        if(passwordEncoder.matches(oldPass,admin.getPassword())){
            admin.setPassword(passwordEncoder.encode(newPass));
            int i = adminMapper.updateById(admin);
            if(i!=0){
                return true;
            }
        }
        return false;
    }
}
