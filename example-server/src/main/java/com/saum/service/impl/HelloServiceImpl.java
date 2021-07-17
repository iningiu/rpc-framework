package com.saum.service.impl;

import com.saum.Hello;
import com.saum.HelloService;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author saum
 * @Date 2021/7/17
 */
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(Hello hello) {
        log.info("HelloServiceImpl收到: {}.", hello.getMessage());
        String result = "Hello description is " + hello.getDescription();
        log.info("HelloServiceImpl返回: {}.", result);
        return result;
    }
}
