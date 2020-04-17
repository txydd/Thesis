package com.cxq.viewer.utils;

import java.util.UUID;

public class GUIDUtil
{
    public static String generateGUID()
    {
        String uuid = UUID.randomUUID().toString();
        //去掉“-”符号
        return uuid.replaceAll("-", "");
    }
}
