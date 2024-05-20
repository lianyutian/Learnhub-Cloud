package com.learnhub.learning.utils;

import lombok.Data;

import java.time.Duration;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟任务
 *
 * @author lm
 * @since 2024-05-14 14:51:49
 * @version 1.0
 */
@Data
public class DelayTask<D> implements Delayed {

    private final D data;
    private final long deadlineNanos;

    public DelayTask(D data, Duration delayTime) {
        this.data = data;
        this.deadlineNanos = System.nanoTime() + delayTime.toNanos();
    }

    /**
     * 获取延迟任务的剩余延迟时间
     *
     * @param unit the time unit
     * @return 剩余延迟时间
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(Math.max(0, deadlineNanos - System.nanoTime()), TimeUnit.NANOSECONDS);
    }

    /**
     * 比较两个延迟任务的延迟时间，判断执行顺序
     *
     * @param o the object to be compared.
     * @return 执行顺序
     */
    @Override
    public int compareTo(Delayed o) {
        long l = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        if (l > 0) {
            return 1;
        } else if (l < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
