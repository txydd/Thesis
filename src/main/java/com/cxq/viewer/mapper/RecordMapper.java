package com.cxq.viewer.mapper;

import com.cxq.viewer.domain.Record;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordMapper {
    int insert(Record record);

    int insertSelective(Record record);
    List<Record> getRecord(@Param("name")String name,@Param("owner")String owner, @Param("start")Integer start, @Param("count")Integer count);
    int getTotalPage(@Param("name")String name,@Param("owner")String owner);
}