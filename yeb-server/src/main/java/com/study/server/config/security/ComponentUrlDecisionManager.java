package com.study.server.config.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 权限控制
 * 判断用户角色
 */
@Component
public class ComponentUrlDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for(ConfigAttribute configAttribute : configAttributes){
//            当前URL所需要的权限
            String needAttribute = configAttribute.getAttribute();
            if("ROLE_LOGIN".equals(needAttribute)){
//                判断用户是否登录
                if(authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("请先登录");
                }else{
                    return;
                }
            }
//            判断角色是否是满足URL所需要的权限
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if(authority.getAuthority().equals(needAttribute)){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
