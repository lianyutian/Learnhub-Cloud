package com.learnhub.remark.service.impl;

import com.learnhub.common.exceptions.DbException;
import com.learnhub.common.utils.UserContext;
import com.learnhub.remark.constants.RedisConstants;
import com.learnhub.remark.domain.dto.LikeRecordFormDTO;
import com.learnhub.remark.mapper.LikedRecordMapper;
import com.learnhub.remark.service.ILikedRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/10 17:35
 */
@Service
@Slf4j
@AllArgsConstructor
public class LikedRecordServiceRedisImpl implements ILikedRecordService {

    private final LikedRecordMapper likedRecordMapper;
    private final StringRedisTemplate stringRedisTemplate;

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
        return null;
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
