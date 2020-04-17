package com.cxq.viewer.services;

import com.cxq.viewer.domain.Record;

import java.util.List;

public interface RecordService {
    int addRecord(Record record);
    List<Record> getRecord(String name,String owner,Integer pageNum);
    int getTotalPage(String name,String owner);

}
