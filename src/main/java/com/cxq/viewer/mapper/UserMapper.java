package com.cxq.viewer.mapper;

import com.cxq.viewer.domain.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int insert(Users record);
    int insertSelective(Users record);
    Users getUserByName(@Param("name")String name);
    List<Users> getUser(@Param("name")String name, @Param("start")Integer start, @Param("count")Integer count);
    int getTotalPage(@Param("name")String name);
    int updateUser(Users user);
}