package com.learnhub.common.utils;

import org.slf4j.MDC;

import java.util.Map;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/29 21:18]
 */
public class MarkedRunnable implements Runnable {
    private final Runnable runnable;
    private final Map<String, String> context;

    public MarkedRunnable(Runnable runnable) {
        this.runnable = runnable;
        this.context = MDC.getCopyOfContextMap();
    }

    @Override
    public void run() {
        if (context == null) {
            MDC.clear();
        } else {
            MDC.setContextMap(context);
        }
        try {
            runnable.run();
        } finally {
            MDC.clear();
        }
    }
}
