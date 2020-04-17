package com.cxq.viewer.mapper;

import com.cxq.viewer.domain.Files;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilesMapper {
    int insert(Files record);

    int insertSelective(Files record);
    List<Files> getFile(@Param("name")String name, @Param("start")Integer start, @Param("count")Integer count);
    int getTotalPage(@Param("name")String name);
    int deleteFile(@Param("id")String id);
}