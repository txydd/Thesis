package com.cxq.viewer.mapper;

import com.cxq.viewer.domain.Entropy;
import org.apache.ibatis.annotations.Param;

public interface EntropyMapper {
    int insert(Entropy record);

    int insertSelective(Entropy record);
    Entropy getEntropy(@Param("id")String id);
    int updateEntropy(Entropy entropy);
}