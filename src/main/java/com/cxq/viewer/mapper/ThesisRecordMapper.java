package com.cxq.viewer.mapper;

import com.cxq.viewer.domain.ThesisRecord;

public interface ThesisRecordMapper {
    int insert(ThesisRecord record);

    int insertSelective(ThesisRecord record);
}