package com.cxq.viewer.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Slf4j
public class AuthenticationUtil
{
    public static Authentication getAuthentication()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            log.warn("无法获取权限信息");
        }
        return auth;
    }

    public static String getUsername()
    {
        Authentication auth = getAuthentication();
        if (auth == null) {
            return null;
        }
        Object obj = auth.getPrincipal();
        if (obj == null || "anonymousUser".equals(obj.toString())) {
            log.warn("用户无效");
            return null;
        }
        User user = (User) obj;
        return user.getUsername();
    }

    public static String getUserRole()
    {
        Authentication auth = getAuthentication();
        if (auth == null) {
            return null;
        }
        String role = auth.getAuthorities().toString();
        return role;
    }
}
