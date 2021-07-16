package com.saum.registry;

import java.net.InetSocketAddress;

/**
 * 服务注册接口
 */
public interface ServiceRegistry {
    void registryService(String rpcServiceName, InetSocketAddress inetSocketAddress);
}
