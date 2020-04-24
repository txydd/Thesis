package com.cxq.viewer.mapper;

import com.cxq.viewer.domain.Record;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface RecordMapper {
    int insert(Record record);

    int insertSelective(Record record);
    List<Record> getRecord(@Param("name")String name,@Param("owner")String owner, @Param("isRead")String isRead,@Param("start")Integer start, @Param("count")Integer count);
    List<Record> getRecord1(@Param("name")String name, @Param("owner")String owner, @Param("date1") Date date1 , @Param("date2")Date date2, @Param("isRead")String isRead,@Param("start")Integer start, @Param("count")Integer count);
    int getTotalPage(@Param("name")String name,@Param("owner")String owner,@Param("isRead")String isRead);
    int getTotalPage1(@Param("name")String name,@Param("owner")String owner, @Param("date1") Date date1 , @Param("date2")Date date2, @Param("isRead")String isRead);
    Record getById(@Param("id")String id);
    int updateRecord(Record record);
}