package com.learnhub.remark.service.impl;

import com.learnhub.api.dto.remark.RemarkMQMessageDTO;
import com.learnhub.common.autoconfigure.mq.RocketMQEnhanceTemplate;
import com.learnhub.common.exceptions.DbException;
import com.learnhub.common.utils.StringUtils;
import com.learnhub.common.utils.UserContext;
import com.learnhub.remark.domain.dto.LikeRecordFormDTO;
import com.learnhub.remark.domain.po.LikedRecord;
import com.learnhub.remark.mapper.LikedRecordMapper;
import com.learnhub.remark.service.ILikedRecordService;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.learnhub.common.constants.MqConstants.Topic.LIKE_RECORD_TOPIC;
import static com.learnhub.common.constants.MqConstants.Key.LIKED_TIMES_KEY_TEMPLATE;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/1 16:09
 */
//@Service
@AllArgsConstructor
public class LikedRecordServiceImpl implements ILikedRecordService {

    private final LikedRecordMapper likedRecordMapper;
    private final RocketMQEnhanceTemplate rocketMQEnhanceTemplate;

    @Override
    @Transactional(rollbackFor = {DbException.class, Exception.class})
    public void addLikeRecord(LikeRecordFormDTO likeRecordFormDTO) {
        // 1.基于前端的参数，判断是执行点赞还是取消点赞
        boolean success = likeRecordFormDTO.getLiked() ? like(likeRecordFormDTO) : unlike(likeRecordFormDTO);
        // 2.判断是否执行成功，如果失败，则直接结束
        if (!success) {
            return;
        }
        // 3.如果执行成功，统计点赞总数
        int likes = likedRecordMapper.countLikeRecord(UserContext.getUserId(), likeRecordFormDTO.getBizId());
        // 4.发送MQ通知
        RemarkMQMessageDTO remarkMessage = new RemarkMQMessageDTO();
        // 设置业务key
        remarkMessage.setKey(String.valueOf(likeRecordFormDTO.getBizId()));
        // 设置消息来源，便于查询
        remarkMessage.setSource("remark");
        // 业务消息内容
        remarkMessage.setBizId(likeRecordFormDTO.getBizId());
        remarkMessage.setLikes(likes);

        rocketMQEnhanceTemplate.send(
                LIKE_RECORD_TOPIC,
                StringUtils.format(LIKED_TIMES_KEY_TEMPLATE, likeRecordFormDTO.getBizType()),
                remarkMessage
        );
    }

    @Override
    public Set<Long> isBizLiked(List<Long> bizIds) {
        // 1.获取登录用户id
        Long userId = UserContext.getUserId();
        // 2.查询点赞状态
        return likedRecordMapper.queryLikeRecordByBizIds(bizIds, userId);
    }

    @Override
    public void readLikedTimesAndSendMessage(String bizType, int maxBizSize) {

    }

    private boolean like(LikeRecordFormDTO likeRecordFormDTO) {
        Long userId = UserContext.getUserId();
        // 1.查询点赞记录
        int count = likedRecordMapper.countLikeRecord(userId, likeRecordFormDTO.getBizId());

        // 2.判断是否存在，如果已经存在，直接结束
        if (count > 0) {
            return false;
        }

        // 3.如果不存在，直接新增
        LikedRecord likedRecord = new LikedRecord();
        likedRecord.setUserId(userId);
        likedRecord.setBizId(likeRecordFormDTO.getBizId());
        likedRecord.setBizType(likeRecordFormDTO.getBizType());

        likedRecordMapper.saveLikedRecord(likedRecord);
        return true;
    }

    private boolean unlike(LikeRecordFormDTO likeRecordFormDTO) {
        return likedRecordMapper.deleteLikedRecord(UserContext.getUserId(), likeRecordFormDTO.getBizId());
    }
}
