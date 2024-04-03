package com.learnhub.common.validate;

import com.learnhub.common.utils.ArrayUtils;
import com.learnhub.common.validate.annotations.EnumValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/3 14:54
 */
@Slf4j
public class EnumValueValidator implements ConstraintValidator<EnumValid, Integer> {
    private int[] enums = null;

    @Override
    public void initialize(EnumValid enumValid) {
        this.enums = enumValid.enumeration();
        log.info("payload>>{}", ArrayUtils.toString(enumValid.payload()));
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // 不做空校验
        if (value == null) {
            return true;
        }
        //没有配置枚举值不校验
        if (ArrayUtils.isEmpty(enums)) {
            return true;
        }
        for (int e : enums) {
            if (e == value) {
                return true;
            }
        }
        return false;
    }
}
