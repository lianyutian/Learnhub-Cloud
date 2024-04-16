package com.learnhub.learning.utils;

/**
 * 3
 *
 * @author liming
 * @version 1.0
 * @since 2024/4/16 16:47
 */
public class TableInfoContext {
    private static final ThreadLocal<String> TL = new ThreadLocal<>();

    public static void setInfo(String info) {
        TL.set(info);
    }

    public static String getInfo() {
        return TL.get();
    }

    public static void remove() {
        TL.remove();
    }
}
