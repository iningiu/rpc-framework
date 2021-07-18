package com.saum.remoting.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description: rpc 请求实体类
 */

@Data
@Builder
public class RpcRequest implements Serializable {
    private static final long serialVersionUID = 4645054410084534863L;
    private String requestId;
    /*
     * 要调用的目标类的名称
     */
    private String interfaceName;
    /*
     * 要调用的目标方法名
     */
    private String methodName;
    /*
     * 要调用的目标方法的参数
     */
    private Object[] parameters;
    /*
     * 要调用的目标方法的参数类型
     */
    private Class<?>[] paramTypes;

    public String getRpcServiceName(){
        return this.getInterfaceName();
    }
}
