package com.cxq.viewer.authentication;


import com.cxq.viewer.services.UserService;
import com.cxq.viewer.utils.RequestUtil;
import com.cxq.viewer.utils.UserPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class MyAuthenticationProvider implements AuthenticationProvider
{
    @Autowired
    @Qualifier("myDetailService")
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        try{
            String password=authentication.getCredentials().toString();
            String username=authentication.getName();
            System.out.println("----------------------"+password+"-----------"+username);

            // 根据用户输入的用户名获取该用户名已经在服务器上存在的用户详情，如果没有则返回null
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            String encodePwd=userDetails.getPassword();
            if(userDetails == null){
                return null;
            }
            String role = userDetails.getAuthorities().toString();
            System.out.println(role);
            if(role.equals("[ROLE_ADMIN]")) {


                    //Users admin= userService.getUserByEmployeeId(username);
                   if(password.equals(encodePwd)){

                       HttpSession session = RequestUtil.getRequest().getSession();
                       session.setAttribute("role", role);
                       session.setAttribute("username", username);
                       session.setAttribute("password", password);
                       UserPool.updateOnlineUser(username);

                   }

                // log.info(sb.toString());
            }
            else{


                    if (password.equals(encodePwd)) {


                        HttpSession session = RequestUtil.getRequest().getSession();
                        session.setAttribute("role", role);
                        session.setAttribute("username", username);
                        session.setAttribute("password", password);
                        UserPool.updateOnlineUser(username);

                    } else {
                        return null;

                    }
            }
            return new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());

        }catch (Exception e){
            log.error(e.toString(), e);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return true;
    }
}
