package com.learnhub.common.utils;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/22 14:40
 */
public class UserContext {
    private static final ThreadLocal<Long> TL = new ThreadLocal<>();

    /**
     * 保存用户id
     *
     * @param userId 用户id
     */
    public static void setUserId(Long userId){
        TL.set(userId);
    }

    /**
     * 获取用户id
     *
     * @return 用户id
     */
    public static Long getUserId(){
        return TL.get();
    }

    /**
     * 移除用户id
     */
    public static void removeUserId(){
        TL.remove();
    }
}
