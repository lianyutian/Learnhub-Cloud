package com.learnhub.remark.service.impl;

import com.learnhub.api.dto.remark.LikedTimesDTO;
import com.learnhub.common.autoconfigure.mq.RabbitMqHelper;
import com.learnhub.common.utils.StringUtils;
import com.learnhub.common.utils.UserContext;
import com.learnhub.remark.domain.dto.LikeRecordFormDTO;
import com.learnhub.remark.domain.po.LikedRecord;
import com.learnhub.remark.mapper.LikedRecordMapper;
import com.learnhub.remark.service.ILikedRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.learnhub.common.constants.MqConstants.Exchange.LIKE_RECORD_EXCHANGE;
import static com.learnhub.common.constants.MqConstants.Key.LIKED_TIMES_KEY_TEMPLATE;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/1 16:09
 */
@Service
@AllArgsConstructor
public class LikedRecordServiceImpl implements ILikedRecordService {

    private final LikedRecordMapper likedRecordMapper;
    private final RabbitMqHelper mqHelper;

    @Override
    public void addLikeRecord(LikeRecordFormDTO likeRecordFormDTO) {
        // 1.基于前端的参数，判断是执行点赞还是取消点赞
        boolean success = likeRecordFormDTO.getLiked() ? like(likeRecordFormDTO) : unlike(likeRecordFormDTO);
        // 2.判断是否执行成功，如果失败，则直接结束
        if (!success) {
            return;
        }
        // 3.如果执行成功，统计点赞总数
        Integer likedTimes = likedRecordMapper.countLikeRecord(UserContext.getUser(), likeRecordFormDTO.getBizId());
        // 4.发送MQ通知
        mqHelper.send(
                LIKE_RECORD_EXCHANGE,
                StringUtils.format(LIKED_TIMES_KEY_TEMPLATE, likeRecordFormDTO.getBizType()),
                LikedTimesDTO.of(likeRecordFormDTO.getBizId(), likedTimes));
    }

    private boolean like(LikeRecordFormDTO likeRecordFormDTO) {
        Long userId = UserContext.getUser();
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
        if (like(likeRecordFormDTO)) {
            return likedRecordMapper.deleteLikedRecord(UserContext.getUser(), likeRecordFormDTO.getBizId());
        }
        return false;
    }
}
