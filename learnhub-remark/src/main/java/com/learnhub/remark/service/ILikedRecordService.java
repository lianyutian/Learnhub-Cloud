package com.learnhub.remark.service;

import com.learnhub.remark.domain.dto.LikeRecordFormDTO;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/1 16:08
 */
public interface ILikedRecordService {
    /**
     * 添加一条点赞记录
     *
     * @param likeRecordFormDTO 点赞记录
     */
    void addLikeRecord(LikeRecordFormDTO likeRecordFormDTO);
}
