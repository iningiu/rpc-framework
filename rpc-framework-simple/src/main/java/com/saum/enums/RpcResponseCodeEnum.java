package com.saum.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description: rpc调用结果响应状态码枚举类
 */
@AllArgsConstructor
@Getter
@ToString
public enum RpcResponseCodeEnum {

    SUCCESS(200, "The remote call is successful."),
    FAIL(500, "The remote call is fail.");

    private final int code;
    private final String message;
}
