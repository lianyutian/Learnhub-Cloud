package com.learnhub.common.enums;

import com.learnhub.common.constants.ErrorInfo;
import com.learnhub.common.exceptions.BadRequestException;
import lombok.Getter;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/28 19:53]
 */
@Getter
public enum UserType implements BaseEnum {
    /**
     * 用户类型
     */
    STAFF(1, "其他员工"),
    STUDENT(2, "学生"),
    TEACHER(3, "老师"),
    ;

    final int value;

    final String desc;

    UserType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static UserType of(int value) {
        for (UserType type : UserType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new BadRequestException(ErrorInfo.Msg.INVALID_USER_TYPE);
    }
}
