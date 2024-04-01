package com.learnhub.auth.controller;

import cn.hutool.core.codec.Base64;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;

/**
 * @author : [lm]
 * @version : [v1.0]
 * @createTime : [2024/4/1 20:09]
 */
@RestController
@RequestMapping("jwks")
@AllArgsConstructor
@Hidden
public class JwkController {
    private final KeyPair keyPair;

    @GetMapping
    public String getJwk(){
        // TODO 可以加入clientId和clientSecret校验
        // 获取公钥并转码
        return Base64.encode(keyPair.getPublic().getEncoded());
    }
}
