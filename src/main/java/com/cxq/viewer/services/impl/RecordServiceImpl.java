package com.cxq.viewer.services.impl;

import com.cxq.viewer.domain.Record;
import com.cxq.viewer.mapper.RecordMapper;
import com.cxq.viewer.services.RecordService;

import com.cxq.viewer.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;
    int count=10;
    int count1=0;
    @Override
    public int addRecord(Record record) {
        int t=recordMapper.insertSelective(record);
        return t;
    }

    @Override
    public List<Record> getRecord(String name,String owner, String isRead,Integer pageNum) {
        int start = (pageNum - 1) * count;
        List<Record> list=recordMapper.getRecord(name,owner,isRead,start,count);
        return list;
    }

    @Override
    public List<Record> getRecord1(String name, String owner, String chanceMin, String chanceMax, String startTime, String endTime, String isRead, Integer pageNum) {
        int start = (pageNum - 1) * count;
        Date date1,date2;
        if("".equals(startTime)||startTime == null){
            date1 = null;
        }else{
            date1 = DateUtil.stringToDate(startTime);
        }
        if("".equals(endTime)||endTime == null){
            date2 = null;
        }else{
            date2 = DateUtil.stringToDate(endTime);
        }
        List<Record> list=recordMapper.getRecord1(name,owner,date1,date2,isRead,start,count);
        if("".equals(chanceMin)&&"".equals(chanceMax)){
            count1=list.size();
            return list;
        }
        double t1=0,t2=100;
        if(!"".equals(chanceMin))
         //t1=Integer.parseInt(chanceMin.substring(0,chanceMin.length()-1));
            t1=Double.parseDouble(chanceMin);
        double min=  t1/100;
        if(!"".equals(chanceMax))
        //t2=Integer.parseInt(chanceMax.substring(0,chanceMax.length()-1));
            t2=Double.parseDouble(chanceMax);
        double max=  t2/100;
        List<Record> list1=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            String s=list.get(i).getChance();
            double t=Double.parseDouble(s.substring(0,s.length()-1));
            double chance1=  t/100;
            if(chance1>=min&&chance1<=max){
                list1.add(list.get(i));
            }

        }
        return list1;

    }


    @Override
    public int getTotalPage(String name,String owner,String isRead) {
        int totalCount=recordMapper.getTotalPage(name,owner,isRead);
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
    public int getTotalPage1(String name, String owner, String chanceMin, String chanceMax, String startTime, String endTime, String isRead) {
        Date date1,date2;
        if("".equals(startTime)||startTime == null){
            date1 = null;
        }else{
            date1 = DateUtil.stringToDate(startTime);
        }
        if("".equals(endTime)||endTime == null){
            date2 = null;
        }else{
            date2 = DateUtil.stringToDate(endTime);
        }
        //int totalCount=recordMapper.getTotalPage1(name,owner,date1,date2,isRead);
       int totalCount=count1;
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
    public Record getById(String id) {
        Record record = recordMapper.getById(id);
        return record;
    }

    @Override
    public int updateRecord(Record record) {
        int t=recordMapper.updateRecord(record);
        return t;
    }
}
