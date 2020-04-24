package com.cxq.viewer.services.impl;

import com.cxq.viewer.domain.Thesis;
import com.cxq.viewer.mapper.ThesisMapper;
import com.cxq.viewer.services.ThesisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThesisServiceImpl implements ThesisService {
    @Autowired
    private ThesisMapper thesisMapper;
    int count=10;
    @Override
    public int addThesis(Thesis thesis) {
        int t=thesisMapper.insertSelective(thesis);
        return t;
    }

    @Override
    public List<Thesis> getThesis(String id, String owner,String status,Integer pageNum) {
        int start = (pageNum - 1) * count;
        List<Thesis> list=thesisMapper.getThesis(id,owner,status,start,count);
        return list;
    }

    @Override
    public int getTotalPage(String id, String owner,String status) {
        int totalCount=thesisMapper.getTotalPage(id,owner,status);
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
    public int deleteThesis(String id) {
        int t=thesisMapper.deleteThesis(id);
        return t;
    }

    @Override
    public Thesis getThesisById(String id) {
        Thesis thesis=thesisMapper.selectThesisById(id);
        return thesis;
    }

    @Override
    public int updateThesis(Thesis thesis) {
        int t=thesisMapper.updateThesis(thesis);
        return t;
    }
}
