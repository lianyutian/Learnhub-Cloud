package com.learnhub.remark.domain.po;

import com.learnhub.common.autoconfigure.mybatis.plugin.AutoId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/1 16:54
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LikedRecord {
    @AutoId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 点赞的业务id
     */
    private Long bizId;

    /**
     * 点赞的业务类型
     */
    private String bizType;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
