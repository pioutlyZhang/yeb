package com.study.server.config.security;

import com.study.server.pojo.Menu;
import com.study.server.pojo.Role;
import com.study.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

@Component
public class CustomerFilter implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private IMenuService menuService;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 获取某个安全对象的权限信息
     * @param object
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        获取请求的URL
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        for (Menu menu : menuService.getMenuWithRole()) {
            //判断URL是否匹配
            if(antPathMatcher.match(menu.getUrl(),requestUrl)){
                String[] roles = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
                List<ConfigAttribute> list = SecurityConfig.createList(roles);
                return list;
            }
        }
        //没有匹配默认登录即可访问
        return SecurityConfig.createList("ROLE_LOGIN");

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
