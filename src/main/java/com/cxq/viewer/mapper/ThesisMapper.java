package com.cxq.viewer.mapper;

import com.cxq.viewer.domain.Thesis;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ThesisMapper {
    int insert(Thesis record);

    int insertSelective(Thesis record);

    List<Thesis> getThesis(@Param("id")String id,@Param("owner")String owner,@Param("status")String status,@Param("start")Integer start,@Param("count")Integer count);
    int getTotalPage(@Param("id")String id,@Param("owner")String owner,@Param("status")String status);
    int deleteThesis(@Param("id")String id);
}