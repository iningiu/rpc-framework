package com.saum.remoting.dto;

import com.saum.enums.RpcResponseCodeEnum;
import lombok.*;

import java.io.Serializable;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class RpcResponse<T> implements Serializable {
    private static final long serialVersionUID = -2662281472656945732L;
    private String requestId;
    private Integer code;
    private String message;
    private T data;

    public static <T> RpcResponse<T> success(T data, String requestId){
        RpcResponse<T> rpcResponse = new RpcResponse<>();
        rpcResponse.setRequestId(requestId);
        rpcResponse.setCode(RpcResponseCodeEnum.SUCCESS.getCode());
        rpcResponse.setMessage(RpcResponseCodeEnum.SUCCESS.getMessage());
        if(null != data){
            rpcResponse.setData(data);
        }
        return rpcResponse;
    }

    public static <T> RpcResponse<T> fail(){
        RpcResponse<T> rpcResponse = new RpcResponse<>();
        rpcResponse.setCode(RpcResponseCodeEnum.FAIL.getCode());
        rpcResponse.setMessage(RpcResponseCodeEnum.FAIL.getMessage());
        return rpcResponse;
    }
}
