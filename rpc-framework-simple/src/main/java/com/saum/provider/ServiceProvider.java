package com.saum.provider;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description: 暴露服务的服务提供方
 */
public interface ServiceProvider {

    void addService(Object rpcService);

    Object getService(String rpcServiceName);

    /**
    * @Description 向注册中心注册提供的服务
    */
    void publishService(Object rpcService);
}
