package com.learnhub.auth.task;

import com.learnhub.auth.common.domain.dto.PrivilegeRoleDTO;
import com.learnhub.auth.service.IPrivilegeService;
import com.learnhub.auth.utils.PrivilegeCache;
import com.learnhub.common.utils.CollUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 加载权限任务
 *
 * @author liming
 * @version 1.0
 * @since 2024/4/2 13:49
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoadPrivilegeTask {
    private final IPrivilegeService privilegeService;
    private final PrivilegeCache privilegeCache;

    @PostConstruct
    public void loadPrivilegeCache(){
        try {
            log.trace("开始更新权限缓存数据");
            // 1.查询数据
            List<PrivilegeRoleDTO> privilegeRoleDTOList = privilegeService.queryPrivilegeRoleList();
            if (CollUtils.isEmpty(privilegeRoleDTOList)) {
                return;
            }
            // 2.缓存
            privilegeCache.initPrivilegesCache(privilegeRoleDTOList);
            log.trace("更新权限缓存数据成功！");
        }catch (Exception e){
            log.error("更新权限缓存数据失败！原因：{}", e.getMessage());
        }
    }
}
