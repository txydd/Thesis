package com.cxq.viewer.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class UserPool
{
    /**
     *  在线用户池 用于存储在线用户的id及其最后一次操作的时间戳
     */
    public final static Map<String, Long> onlineUserPool=new HashMap<>();

    /**
     *  活跃用户池 用于存储正在执行token刷新操作的用户id
     */
    public final static Set<String> activeUserPool=new HashSet();

    /**
     *  更新在线用户最后一次的操作时间戳
     * @param userName 用户登录账号
     * @return 如果无错返回True 否则日志记录异常并且返回False
     */
    public static Boolean updateOnlineUser(String userName)
    {
        try {
            System.out.println(userName+"更新");
            onlineUserPool.put(userName, System.currentTimeMillis());
            return Boolean.TRUE;
        }catch (Exception e){
            log.error(e.toString(), e);
            return Boolean.FALSE;
        }
    }

    /**
     *  遍历onlineUserPool在线用户的最后一次操作时间戳，如果超过5分钟，则从此列表清除
     * @return 返回所有需要清除的用户名
     */
    public static List<String> checkAllInactiveUser()
    {
        List<String> deadUsers=new ArrayList<>();
        try {
            Set<Map.Entry<String,Long>> entries=onlineUserPool.entrySet();
            for (Map.Entry<String,Long> entry:entries){
                String userName=entry.getKey();
                Long lastTime=entry.getValue();
                System.out.println(userName+" "+lastTime);
                Long now=System.currentTimeMillis();
                if (now-lastTime>300000){ //超过5分钟没有更新时间戳 那么就注销此用户
                    deadUsers.add(userName);
                }
            }
            return deadUsers;
        }catch (Exception e){
            log.error(e.toString(), e);
            return deadUsers;
        }
    }

    /**
     *  从在线用户池中移除相应的用户名
     * @param userName 用户登录账号
     * @return 如果无错返回True 否则日志记录异常并且返回False
     */
    public static Boolean logoutUser(String userName)
    {
        try{
            if (onlineUserPool.containsKey(userName)){
                onlineUserPool.remove(userName);
            }
            return Boolean.TRUE;
        }catch (Exception e){
            log.error(e.toString(), e);
            return Boolean.FALSE;
        }
    }

    /**
     *  尝试获取到刷新token方法的锁
     * @param userName 用户登录账号
     * @return 获取到锁返回True 否则 False
     */
    public static Boolean getActiveLock(String userName)
    {
        if (activeUserPool.contains(userName)){
            return Boolean.FALSE;
        }else{
            activeUserPool.add(userName);
            return Boolean.TRUE;
        }
    }

    /**
     *  尝试释放临界锁
     * @param userName 用户登录账号
     * @return 成功释放返回True 否则返回False
     */
    public static Boolean releaseActiveLock(String userName)
    {
        try{
            Boolean res=Boolean.TRUE;
            if (activeUserPool.contains(userName)){
                res=activeUserPool.remove(userName);
            }
            return res;
        }catch (Exception e){
            log.error(e.toString(), e);
            return Boolean.FALSE;
        }
    }
}
