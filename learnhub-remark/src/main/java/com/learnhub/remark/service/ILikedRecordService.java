package com.learnhub.remark.service;

import com.learnhub.remark.domain.dto.LikeRecordFormDTO;

import java.util.List;
import java.util.Set;

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

    /**
     * 查询当前用户是否点赞了指定的业务
     *
     * @param bizIds 业务id集合
     * @return 点赞状态
     */
    Set<Long> isBizLiked(List<Long> bizIds);

    /**
     * 计算业务点赞数，并发送MQ
     *
     * @param bizType 业务类型
     * @param maxBizSize 一次计算点赞最大数
     */
    void readLikedTimesAndSendMessage(String bizType, int maxBizSize);
}
