package com.cxq.viewer.services;

import com.cxq.viewer.domain.Files;

import java.util.List;

public interface FileService {
    int addFile(Files file);
    List<Files> getFile(String name,String key1,String key2,String key3,Integer pageNum);
    int getTotalPage(String name,String key1,String key2,String key3);
    int deleteFile(String id);
}
