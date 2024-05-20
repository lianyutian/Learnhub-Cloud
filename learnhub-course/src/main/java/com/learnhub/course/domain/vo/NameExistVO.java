package com.learnhub.course.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * NameExistVO
 *
 * @author lm
 * @since 2024-05-15 17:12:39
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NameExistVO {
    public static final NameExistVO EXISTED = new NameExistVO(true);
    public static final NameExistVO NOT_EXIST = new NameExistVO(false);

    private Boolean existed;
}
