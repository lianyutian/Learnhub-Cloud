package com.learnhub.remark.mapper;

import com.learnhub.remark.domain.po.LikedRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/1 16:50
 */
@Mapper
public interface LikedRecordMapper {

    /**
     * 根据用户id和业务id查询点赞数
     *
     * @param userId 用户id
     * @param bizId 业务id
     * @return 点赞数
     */
    int countLikeRecord(Long userId, Long bizId);

    /**
     * 保存点赞记录
     *
     * @param likedRecord 点赞记录
     */
    void saveLikedRecord(LikedRecord likedRecord);

    /**
     * 删除点赞记录
     *
     * @param userId 用户id
     * @param bizId 业务id
     * @return 删除结果
     */
    boolean deleteLikedRecord(Long userId, Long bizId);

    /**
     * 查询用户点赞业务
     *
     * @param bizIds 业务id
     * @param userId 用户id
     * @return 点赞业务集合
     */
    Set<Long> queryLikeRecordByBizIds(List<Long> bizIds, Long userId);
}
