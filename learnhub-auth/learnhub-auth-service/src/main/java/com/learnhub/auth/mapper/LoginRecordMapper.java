package com.learnhub.auth.mapper;

import com.learnhub.auth.domain.po.LoginRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/3/29 21:03]
 */
@Mapper
public interface LoginRecordMapper {

    /**
     * 保存登录成功记录
     *
     * @param record 登录记录
     */
     void saveLoginSuccessRecord(LoginRecord record);
}
