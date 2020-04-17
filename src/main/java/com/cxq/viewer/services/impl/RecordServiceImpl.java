package com.cxq.viewer.services.impl;

import com.cxq.viewer.domain.Record;
import com.cxq.viewer.mapper.RecordMapper;
import com.cxq.viewer.services.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;
    int count=10;
    @Override
    public int addRecord(Record record) {
        int t=recordMapper.insertSelective(record);
        return t;
    }

    @Override
    public List<Record> getRecord(String name,String owner, Integer pageNum) {
        int start = (pageNum - 1) * count;
        List<Record> list=recordMapper.getRecord(name,owner,start,count);
        return list;
    }

    @Override
    public int getTotalPage(String name,String owner) {
        int totalCount=recordMapper.getTotalPage(name,owner);
        int totalPage = 0;
        if (totalCount % count == 0) {
            totalPage = totalCount / count;
        } else {
            totalPage = totalCount / count + 1;
        }
        totalPage = totalPage == 0 ? 1 : totalPage;
        return totalPage;
    }
}
