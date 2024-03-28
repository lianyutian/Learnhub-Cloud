package com.learnhub.common.constants;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/12 16:31
 */
public interface Constant {
    static final String REQUEST_ID_HEADER = "requestId";
    static final String REQUEST_FROM_HEADER = "x-request-from";

    static final String GATEWAY_ORIGIN_NAME = "gateway";
    static final String FEIGN_ORIGIN_NAME = "feign";

    /**
     * 数据字段 - id
     */
    static final String DATA_FIELD_NAME_ID = "id";

    /**
     * 数据字段 - create_time
     */
    static final String DATA_FIELD_NAME_CREATE_TIME = "create_time";
    static final String DATA_FIELD_NAME_CREATE_TIME_CAMEL = "createTime";

    /**
     * 数据字段 - update_time
     */
    static final String DATA_FIELD_NAME_UPDATE_TIME = "update_time";
    static final String DATA_FIELD_NAME_UPDATE_TIME_CAMEL = "updateTime";

    /**
     * 数据字段 - liked_times
     */
    static final String DATA_FIELD_NAME_LIKED_TIME = "liked_times";
    static final String DATA_FIELD_NAME_LIKED_TIME_CAMEL = "likedTimes";

    /**
     * 数据字段 - creater
     */
    static final String DATA_FIELD_NAME_CREATER = "creater";

    /**
     * 数据字段 - updater
     */
    static final String DATA_FIELD_NAME_UPDATER = "updater";

    /**
     * 数据已经删除标识值
     */
    static final boolean DATA_DELETE = true;
    /**
     * 数据未删除标识值
     */
    static final boolean DATA_NOT_DELETE = false;
    /**
     * 响应结果是否被R标记过
     */
    static final String BODY_PROCESSED_MARK_HEADER = "IS_BODY_PROCESSED";
}
