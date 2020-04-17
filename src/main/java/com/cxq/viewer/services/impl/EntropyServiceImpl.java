package com.cxq.viewer.services.impl;

import com.cxq.viewer.domain.Entropy;
import com.cxq.viewer.mapper.EntropyMapper;
import com.cxq.viewer.services.EntropyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntropyServiceImpl implements EntropyService {
    @Autowired
    private EntropyMapper entropyMapper;
    @Override
    public Entropy getEntropy(String id) {
        Entropy entropy=entropyMapper.getEntropy(id);
        return entropy;
    }

    @Override
    public int updateEntropy(Entropy entropy) {

        int t=entropyMapper.updateEntropy(entropy);
        return t;
    }
}
