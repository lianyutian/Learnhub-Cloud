package com.github.learnhub.user.service.impl;

import com.github.learnhub.user.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @author liming
 * @version 1.0
 * @since 2024/3/27 15:13
 */
@Service
public class UserServiceImpl implements IUserService {
    @Override
    public String hello() {
        return "hello";
    }
}
