package com.saum.exception;

import com.saum.enums.RpcErrorMessageEnum;

/**
 * @Author saum
 * @Date 2021/7/17
 */
public class RpcException extends RuntimeException {
    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum, String detail){
        super(rpcErrorMessageEnum.getMessage() + ":" + detail);
    }

    public RpcException(String message, Throwable cause){
        super(message, cause);
    }

    public RpcException(RpcErrorMessageEnum rpcErrorMessageEnum){
        super(rpcErrorMessageEnum.getMessage());
    }
}
