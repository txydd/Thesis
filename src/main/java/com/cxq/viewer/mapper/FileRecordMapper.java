package com.cxq.viewer.mapper;

import com.cxq.viewer.domain.FileRecord;

public interface FileRecordMapper {
    int insert(FileRecord record);

    int insertSelective(FileRecord record);
}