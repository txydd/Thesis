package com.cxq.viewer.authentication;


import com.cxq.viewer.services.UserService;
import com.cxq.viewer.utils.UserPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("mySecurity")
@Slf4j
public class MySecurity
{
    @Autowired
    private UserService userService;
    /**
     * 通过传入的认证和请求进行拦截操作
     * @param authentication 认证对象
     * @param request http请求对象
     * @return
     */
    public boolean check(Authentication authentication, HttpServletRequest request)
    {
        //如果能获取到Principal对象不为空证明，授权已经通过
        Object principal = authentication.getPrincipal();
        if (principal != null && principal instanceof UserDetails) {
            //获取请求info
            String userName=authentication.getName();
            String requestUrl=request.getRequestURL().toString();
            String requestParams=request.getQueryString();
            String requestMethod=request.getMethod();

            String role=((UserDetails) principal).getAuthorities().toString();
            StringBuilder sb=new StringBuilder(userName);
            sb.append(role).append(" ").append(requestUrl);
            if (requestParams!=null) sb.append("?").append(requestParams);
            sb.append(" ").append(requestMethod);
            log.info(sb.toString());

            UserPool.updateOnlineUser(userName); //刷新在线用户最后一次操作的时间戳
            return true;
        }
        return false;
    }
}
