package com.cxq.viewer.services;

import com.cxq.viewer.domain.Files;

import java.util.List;

public interface FileService {
    int addFile(Files file);
    List<Files> getFile(String name,Integer pageNum);
    int getTotalPage(String name);
    int deleteFile(String id);
}
