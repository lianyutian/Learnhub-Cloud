package com.learnhub.promotion.mapper;

import com.learnhub.promotion.domain.po.ExchangeCode;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/23 16:19
 */
@Mapper
public interface ExchangeCodeMapper {
    /**
     * 保存兑换码
     *
     * @param list 兑换码列表
     */
    void saveExchangeCodes(List<ExchangeCode> list);
}
