package com.learnhub.remark.service.impl;

import com.learnhub.api.dto.remark.RemarkMQMessageDTO;
import com.learnhub.api.dto.remark.RemarkMQMessagesDTO;
import com.learnhub.common.autoconfigure.mq.RocketMQEnhanceTemplate;
import com.learnhub.common.exceptions.DbException;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.common.utils.StringUtils;
import com.learnhub.common.utils.UserContext;
import com.learnhub.remark.constants.RedisConstants;
import com.learnhub.remark.domain.dto.LikeRecordFormDTO;
import com.learnhub.remark.service.ILikedRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.learnhub.common.constants.MqConstants.Key.LIKED_TIMES_KEY_TEMPLATE;
import static com.learnhub.common.constants.MqConstants.Topic.LIKE_RECORD_TOPIC;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/10 17:35
 */
@Service
@Slf4j
@AllArgsConstructor
public class LikedRecordServiceRedisImpl implements ILikedRecordService {

    private final StringRedisTemplate stringRedisTemplate;
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
        Long likedTimes = stringRedisTemplate
                .opsForSet()
                .size(
                        RedisConstants.LIKES_BIZ_KEY_PREFIX + likeRecordFormDTO.getBizId()
                );

        if (likedTimes == null) {
            return;
        }

        // 4.缓存点总数到Redis
        stringRedisTemplate
                .opsForZSet()
                .add(
                        RedisConstants.LIKES_TIMES_KEY_PREFIX + likeRecordFormDTO.getBizType(),
                        likeRecordFormDTO.getBizId().toString(),
                        likedTimes
                );

    }

    @Override
    public Set<Long> isBizLiked(List<Long> bizIds) {
        // 1.获取用户id
        Long userId = UserContext.getUserId();
        // 2.查询点赞状态
        List<Object> objects = stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            StringRedisConnection src = (StringRedisConnection) connection;
            for (Long bizId : bizIds) {
                String key = RedisConstants.LIKES_BIZ_KEY_PREFIX + bizId;
                src.sIsMember(key, userId.toString());
            }
            return null;
        });
        // 3.返回结果
        return IntStream.range(0, objects.size())
                .filter(i -> (boolean) objects.get(i))
                .mapToObj(bizIds::get)
                .collect(Collectors.toSet());
    }

    @Override
    public void readLikedTimesAndSendMessage(String bizType, int maxBizSize) {
        // 1.读取并移除Redis中缓存的点赞总数
        String key = RedisConstants.LIKES_TIMES_KEY_PREFIX + bizType;
        Set<ZSetOperations.TypedTuple<String>> tuples = stringRedisTemplate.opsForZSet().popMin(key, maxBizSize);
        if (CollUtils.isEmpty(tuples)) {
            return;
        }

        // 2.数据转换
        RemarkMQMessagesDTO remarkMQMessagesDTO = new RemarkMQMessagesDTO();
        List<RemarkMQMessageDTO> remarkMessageList = new ArrayList<>(tuples.size());
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            String bizId = tuple.getValue();
            Double likedTimes = tuple.getScore();
            if (bizId == null || likedTimes == null) {
                continue;
            }
            remarkMessageList.add(RemarkMQMessageDTO.of(Long.valueOf(bizId), likedTimes.intValue()));
        }
        remarkMQMessagesDTO.setRemarkMQMessageDTOS(remarkMessageList);

        // 3.发送MQ消息
        rocketMQEnhanceTemplate.send(
                LIKE_RECORD_TOPIC,
                StringUtils.format(LIKED_TIMES_KEY_TEMPLATE, bizType),
                remarkMQMessagesDTO
        );
    }


    private boolean like(LikeRecordFormDTO likeRecordFormDTO) {
        // 1.获取用户id
        Long userId = UserContext.getUserId();
        // 2.获取Key
        String key = RedisConstants.LIKES_BIZ_KEY_PREFIX + likeRecordFormDTO.getBizId();
        // 3.执行 SADD 命令
        Long result = stringRedisTemplate.opsForSet().add(key, userId.toString());
        return result != null && result > 0;
    }

    private boolean unlike(LikeRecordFormDTO likeRecordFormDTO) {
        // 1.获取用户id
        Long userId = UserContext.getUserId();
        // 2.获取Key
        String key = RedisConstants.LIKES_BIZ_KEY_PREFIX + likeRecordFormDTO.getBizId();
        // 3.执行 SREM 命令
        Long result = stringRedisTemplate.opsForSet().remove(key, userId.toString());
        return result != null && result > 0;
    }
}
