package com.cxq.viewer.utils;


import com.cxq.viewer.domain.Users;
import com.cxq.viewer.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class UserUtil
{
    @Autowired
    private UserMapper UserMapper;

    private static UserMapper globalMapper;


    @PostConstruct
    public void init()
    {
        globalMapper = UserMapper;

        log.info("-----UserUtil已经正常配置-----");

    }
    public static Users getCurrUser()
    {
        String userName=AuthenticationUtil.getUsername();
        Users user = globalMapper.getUserByName(userName);
        return user;
    }


}
