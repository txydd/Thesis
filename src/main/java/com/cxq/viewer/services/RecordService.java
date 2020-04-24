package com.cxq.viewer.services;

import com.cxq.viewer.domain.Record;

import java.util.List;

public interface RecordService {
    int addRecord(Record record);
    List<Record> getRecord(String name,String owner,String isRead,Integer pageNum);
    List<Record> getRecord1(String name,String owner,String chanceMin,String chanceMax ,String startTime,String endTime,String isRead,Integer pageNum);


    int getTotalPage(String name,String owner,String isRead);
    int getTotalPage1(String name,String owner,String chanceMin,String chanceMax ,String startTime,String endTime,String isRead);
    Record getById(String id);
    int updateRecord(Record record);

}
