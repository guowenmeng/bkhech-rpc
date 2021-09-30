package com.bkhech.provider.service;

import com.bkhech.api.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author guowm
 * @date 2021/9/18
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    /**
     * 保存用户信息
     *
     * @param name
     */
    @Override
    public String saveUser(String name) {
        log.info("begin save user:{}",name);
        return "save User success: "+name;
    }
}
