package com.learnhub.learning.service.impl;

import com.github.pagehelper.Page;
import com.learnhub.api.client.user.UserClient;
import com.learnhub.api.dto.user.UserDTO;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.common.utils.DateUtils;
import com.learnhub.common.utils.UserContext;
import com.learnhub.learning.constants.LearningConstants;
import com.learnhub.learning.constants.RedisConstants;
import com.learnhub.learning.domain.po.PointsBoard;
import com.learnhub.learning.domain.query.PointsBoardQuery;
import com.learnhub.learning.domain.vo.PointsBoardItemVO;
import com.learnhub.learning.domain.vo.PointsBoardVO;
import com.learnhub.learning.mapper.PointsBoardMapper;
import com.learnhub.learning.service.IPointsBoardService;
import com.learnhub.learning.utils.TableInfoContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.learnhub.learning.constants.LearningConstants.POINTS_BOARD_TABLE_PREFIX;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/16 16:35
 */
@Service
@RequiredArgsConstructor
public class PointsBoardServiceImpl implements IPointsBoardService {

    private final StringRedisTemplate redisTemplate;
    private final PointsBoardMapper pointsBoardMapper;
    private final UserClient userClient;

    @Override
    public PointsBoardVO queryPointsBoardBySeason(PointsBoardQuery query) {
        // 1.判断是否是查询当前赛季
        Long season = query.getSeason();
        boolean isCurrent = season == null || season == 0;
        // 2.获取Redis的Key
        LocalDateTime now = LocalDateTime.now();
        String key = RedisConstants.POINTS_BOARD_KEY_PREFIX + now.format(DateUtils.POINTS_BOARD_SUFFIX_FORMATTER);
        // 2.查询我的积分和排名
        PointsBoard myBoard = isCurrent ?
                // 查询当前榜单（Redis）
                queryMyCurrentBoard(key) :
                // 查询历史榜单（MySQL）
                queryMyHistoryBoard(season);
        // 3.查询榜单列表
        List<PointsBoard> list = isCurrent ?
                queryCurrentBoardList(key, query.getPageNo(), query.getPageSize()) :
                queryHistoryBoardList(query);
        // 4.封装VO
        PointsBoardVO pointsBoardVO = new PointsBoardVO();
        // 4.1.处理我的信息
        if (myBoard != null) {
            pointsBoardVO.setPoints(myBoard.getPoints());
            pointsBoardVO.setRank(myBoard.getRank());
        }
        if (CollUtils.isEmpty(list)) {
            return pointsBoardVO;
        }
        // 4.2.查询用户信息
        Set<Long> uIds = list.stream().map(PointsBoard::getUserId).collect(Collectors.toSet());
        List<UserDTO> users = userClient.queryUserDetailByIds(uIds);
        Map<Long, String> userMap = new HashMap<>(uIds.size());
        if(CollUtils.isNotEmpty(users)) {
            userMap = users.stream().collect(Collectors.toMap(UserDTO::getId, UserDTO::getName));
        }
        // 4.3.转换VO
        List<PointsBoardItemVO> items = new ArrayList<>(list.size());
        for (PointsBoard pointsBoard : list) {
            PointsBoardItemVO pointsBoardItemVO = new PointsBoardItemVO();
            pointsBoardItemVO.setPoints(pointsBoard.getPoints());
            pointsBoardItemVO.setRank(pointsBoard.getRank());
            pointsBoardItemVO.setName(userMap.get(pointsBoard.getUserId()));
            items.add(pointsBoardItemVO);
        }
        pointsBoardVO.setBoardList(items);
        return pointsBoardVO;
    }

    @Override
    public void createPointsBoardTableBySeason(Integer season) {
        pointsBoardMapper.createPointsBoardTable(LearningConstants.POINTS_BOARD_TABLE_PREFIX + season);
    }

    @Override
    public List<PointsBoard> queryCurrentBoardList(String key, Integer pageNo, Integer pageSize) {
        // 1.计算分页
        int from = (pageNo - 1) * pageSize;
        // 2.查询
        Set<ZSetOperations.TypedTuple<String>> tuples = redisTemplate.opsForZSet()
                .reverseRangeWithScores(key, from, from + pageSize - 1);
        if (CollUtils.isEmpty(tuples)) {
            return CollUtils.emptyList();
        }
        // 3.封装
        int rank = from + 1;
        List<PointsBoard> list = new ArrayList<>(tuples.size());
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            String userId = tuple.getValue();
            Double points = tuple.getScore();
            if (userId == null || points == null) {
                continue;
            }
            PointsBoard pointsBoard = new PointsBoard();
            pointsBoard.setUserId(Long.valueOf(userId));
            pointsBoard.setPoints(points.intValue());
            pointsBoard.setRank(rank++);
            list.add(pointsBoard);
        }
        return list;
    }

    private PointsBoard queryMyCurrentBoard(String key) {
        // 1.绑定key
        BoundZSetOperations<String, String> ops = redisTemplate.boundZSetOps(key);
        // 2.获取当前用户信息
        String userId = UserContext.getUserId().toString();
        // 3.查询积分
        Double points = ops.score(userId);
        // 4.查询排名
        Long rank = ops.reverseRank(userId);
        // 5.封装返回
        PointsBoard pointsBoard = new PointsBoard();
        pointsBoard.setPoints(points == null ? 0 : points.intValue());
        pointsBoard.setRank(rank == null ? 0 : rank.intValue() + 1);
        return pointsBoard;
    }

    private PointsBoard queryMyHistoryBoard(Long season) {
        // 1.获取登录用户
        Long userId = UserContext.getUserId();
        // 2.计算表名
        TableInfoContext.setInfo(POINTS_BOARD_TABLE_PREFIX + season);
        // 3.查询数据
        PointsBoard pointsBoard = pointsBoardMapper.queryPointsBoardByUserId(userId);
        if (pointsBoard == null) {
            return null;
        }
        // 4.转换数据
        pointsBoard.setRank(pointsBoard.getId().intValue());
        return pointsBoard;
    }

    private List<PointsBoard> queryHistoryBoardList(PointsBoardQuery query) {
        // 1.计算表名
        String tableName = POINTS_BOARD_TABLE_PREFIX + query.getSeason();
        // 2.查询数据
        Page<PointsBoard> page = query.toMpPage().doSelectPage(
                () -> pointsBoardMapper.queryPointsBoardsByPage(tableName, query)
        );
        // 3.数据处理
        List<PointsBoard> records = page.getResult();
        if (CollUtils.isEmpty(records)) {
            return CollUtils.emptyList();
        }
        records.forEach(record -> record.setRank(record.getId().intValue()));
        return records;
    }
}
