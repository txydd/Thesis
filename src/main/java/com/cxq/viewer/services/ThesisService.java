package com.cxq.viewer.services;

import com.cxq.viewer.domain.Thesis;

import java.util.List;

public interface ThesisService {
    int addThesis(Thesis thesis);
    List<Thesis> getThesis(String id,String owner, String status,Integer pageNum);
    int getTotalPage(String id,String owner,String status);
    int deleteThesis(String id);
    Thesis getThesisById(String id);
    int updateThesis(Thesis thesis);
}
