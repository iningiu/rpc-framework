package com.saum.proxy;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description:
 */
public class Teacher implements People {
    @Override
    public String work() {
        System.out.println("老师教书育人...");
        return "教书";
    }
}
