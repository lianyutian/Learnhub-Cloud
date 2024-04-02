package com.learnhub.auth.domain.po;

import com.learnhub.auth.domain.dto.PrivilegeDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author liming
 * @version 1.0
 * @since 2024/4/2 14:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Privilege {
    /**
     * 主键
     */
    private Long id;

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 说明
     */
    private String intro;

    /**
     * API权限的请求方式
     */
    private String method;

    /**
     * API权限的请求路径
     */
    private String uri;

    /**
     * 是否是内部接口
     */
    private Boolean internal;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者id
     */

    private Long creater;

    /**
     * 更新者id
     */

    private Long updater;

    /**
     * 部门id
     */
    private Long depId;

    /**
     * 逻辑删除，默认0
     */
    private Integer deleted;

    public Privilege() {
    }

    public Privilege(PrivilegeDTO dto) {
        this.id = dto.getId();
        this.menuId = dto.getMenuId();
        this.intro = dto.getIntro();
        this.method = dto.getMethod();
        this.uri = dto.getUri();
        this.internal = dto.getInternal();
    }

    public PrivilegeDTO toDTO(){
        PrivilegeDTO dto = new PrivilegeDTO();
        dto.setId(id);
        dto.setMenuId(menuId);
        dto.setIntro(intro);
        dto.setMethod(method);
        dto.setUri(uri);
        dto.setInternal(internal);
        return dto;
    }
}
