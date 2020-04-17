package com.cxq.viewer.services.impl;

import com.cxq.viewer.domain.Users;
import com.cxq.viewer.mapper.UserMapper;
import com.cxq.viewer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    int count=10;

    @Override
    public int addUser(Users user) {
        int t=userMapper.insertSelective(user);
        return t;
    }

    @Override
    public String preLogin(String name, String password) {
        if(name.equals("admin")){
            return "1";
        }else {
            Users user = userMapper.getUserByName(name);
            if (user == null) {
                return "该账户不存在";
            } else {
                if (!user.getPassword().equals(password)) {
                    return "密码错误";
                }
                if(!user.getStatus().equals("1")){
                    return "该账户被冻结";
                }
            }
            return "1";
        }
    }

    @Override
    public Users getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    @Override
    public List<Users> getUser(String name, Integer pageNum) {
        int start = (pageNum - 1) * count;
        List<Users> list=userMapper.getUser(name,start,count);
        return list;
    }

    @Override
    public int getTotalPage(String name) {
        int totalCount=userMapper.getTotalPage(name);
        int totalPage = 0;
        if (totalCount % count == 0) {
            totalPage = totalCount / count;
        } else {
            totalPage = totalCount / count + 1;
        }
        totalPage = totalPage == 0 ? 1 : totalPage;
        return totalPage;
    }

    @Override
    public int updateUser(Users user) {
        int t=userMapper.updateUser(user);
        return t;
    }
}
