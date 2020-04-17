package com.cxq.viewer.services;

import com.cxq.viewer.domain.Entropy;

public interface EntropyService {
    Entropy getEntropy(String id);
    int updateEntropy(Entropy entropy);
}
