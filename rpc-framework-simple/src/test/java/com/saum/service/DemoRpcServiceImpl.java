package com.saum.service;

public class DemoRpcServiceImpl implements DemoRpcService{
    @Override
    public String hello() {
        return "hello,world!";
    }
}
