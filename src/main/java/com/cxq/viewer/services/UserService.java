package com.cxq.viewer.services;

import com.cxq.viewer.domain.Users;

import java.util.List;


public interface UserService {
    int addUser(Users user);
    String preLogin(String name,String password);
    Users getUserByName(String name);
    List<Users> getUser(String name,Integer pageNum);
    int getTotalPage(String name);
    int updateUser(Users user);

}
