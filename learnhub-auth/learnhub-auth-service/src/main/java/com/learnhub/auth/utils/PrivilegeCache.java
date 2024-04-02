package com.learnhub.auth.utils;

import cn.hutool.json.JSONUtil;
import com.learnhub.auth.common.domain.dto.PrivilegeRoleDTO;
import com.learnhub.common.utils.CollUtils;
import com.learnhub.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.learnhub.auth.common.constants.JwtConstants.AUTH_PRIVILEGE_KEY;
import static com.learnhub.auth.common.constants.JwtConstants.AUTH_PRIVILEGE_VERSION_KEY;

/**
 * 权限缓存工具类
 *
 * @author liming
 * @version 1.0
 * @since 2024/4/2 13:51
 */
@Slf4j
@Component
public class PrivilegeCache {
    private final BoundHashOperations<String, String, String> hashOps;
    private final StringRedisTemplate stringRedisTemplate;

    public PrivilegeCache(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.hashOps = stringRedisTemplate.boundHashOps(AUTH_PRIVILEGE_KEY);
    }

    public void initPrivilegesCache(List<PrivilegeRoleDTO> list) {
        // 1.组装权限对应角色
        Map<String, String> map = new HashMap<>(list.size());
        for (PrivilegeRoleDTO prDTO : list) {
            map.put(prDTO.getId().toString(), JSONUtil.toJsonStr(prDTO));
        }
        // 2.写入 redis
        hashOps.putAll(map);
        // 3.版本递增
        incrementVersion();
    }

    private void incrementVersion() {
        stringRedisTemplate.opsForValue().increment(AUTH_PRIVILEGE_VERSION_KEY, 1);
    }

    public void removePrivilegeCacheById(Long id) {
        hashOps.delete(id);
        incrementVersion();
    }

    public void removePrivilegeCacheByIds(List<Long> ids) {
        hashOps.delete(ids.toArray());
        incrementVersion();
    }

    public void removeCacheByRoleId(Long id) {
        // 查询出所有权限信息
        Map<String, String> cacheMap = hashOps.entries();
        if(CollUtils.isEmpty(cacheMap)){
            return;
        }
        // 记录修改的数据
        Map<String, String> modified = new HashMap<>();
        for (Map.Entry<String, String> en : cacheMap.entrySet()) {
            // 获取权限数据
            String value = en.getValue();
            PrivilegeRoleDTO prDTO = JsonUtils.toBean(value, PrivilegeRoleDTO.class);
            // 尝试移除角色id
            boolean remove = prDTO.getRoles().remove(id);
            if(remove){
                modified.put(en.getKey(), JsonUtils.toJsonStr(prDTO));
            }
        }
        // 写回缓存
        hashOps.putAll(modified);
        incrementVersion();
    }

}
