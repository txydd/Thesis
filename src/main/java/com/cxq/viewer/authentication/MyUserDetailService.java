package com.cxq.viewer.authentication;


import com.cxq.viewer.domain.Users;
import com.cxq.viewer.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("myDetailService")
@Slf4j
public class MyUserDetailService implements UserDetailsService
{
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException
    {
        String role;
        String pwd;
        if ("admin".equals(name)) {
            role = "ROLE_ADMIN";
           pwd="123";

        }
        else{
            role = "ROLE_USER";
           Users user = userService.getUserByName(name);
            if(user == null){
                return null;
            }else{
               pwd=user.getPassword();
            }
        }

        User user1 = new User(name, pwd, AuthorityUtils.commaSeparatedStringToAuthorityList(role));

        return user1;
    }


}
