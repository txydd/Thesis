package com.cxq.viewer.services.impl;

import com.cxq.viewer.domain.Files;
import com.cxq.viewer.mapper.FilesMapper;
import com.cxq.viewer.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FilesMapper filesMapper;
    int count=10;
    @Override
    public int addFile(Files file) {
        int t=filesMapper.insertSelective(file);
        return t;
    }

    @Override
    public List<Files> getFile(String name,String key1,String key2,String key3,Integer pageNum) {
        int start = (pageNum - 1) * count;
        List<Files> list=filesMapper.getFile(name,key1,key2,key3,start,count);
        return list;
    }

    @Override
    public int getTotalPage(String name,String key1,String key2,String key3) {
        int totalCount=filesMapper.getTotalPage(name,key1,key2,key3);
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
    public int deleteFile(String id) {
        int t=filesMapper.deleteFile(id);
        return t;
    }


}
