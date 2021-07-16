package com.saum.registry;

import com.saum.remoting.dto.RpcRequest;

import java.net.InetSocketAddress;

/**
 * 服务发现接口
 */
public interface ServiceDiscovery {
    InetSocketAddress lookupService(RpcRequest rpcRequest);
}
