package com.learnhub.common.utils;

import com.learnhub.common.exceptions.BadRequestException;
import jakarta.validation.ConstraintViolation;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 手动执行Violation处理校验结果
 *
 * @author liming
 * @version 1.0
 * @since 2024/3/15 16:23
 */
public class ViolationUtils {

    public static <T> void process(Set<ConstraintViolation<T>> violations) {
        if(CollUtils.isEmpty(violations)){
            return;
        }
        String message = violations
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("|"));
        throw new BadRequestException(message);
    }
}
