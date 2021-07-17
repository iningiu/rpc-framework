package com.saum.provider.impl;

import com.saum.enums.RpcErrorMessageEnum;
import com.saum.exception.RpcException;
import com.saum.provider.ServiceProvider;
import com.saum.registry.ServiceRegistry;
import com.saum.registry.zk.ZkServiceRegistryImpl;
import com.saum.transport.socket.SocketRpcServer;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description: 暴露服务的服务提供方
 */
@Slf4j
public class ZkServiceProviderImpl implements ServiceProvider {

    private final Map<String,Object> serviceMap;
    private final ServiceRegistry serviceRegistry;

    public ZkServiceProviderImpl() {
        this.serviceMap = new ConcurrentHashMap<>();
        this.serviceRegistry = new ZkServiceRegistryImpl();
    }

    @Override
    public void addService(Object rpcService) {
        String rpcServiceName = rpcService.getClass().getInterfaces()[0].getCanonicalName();
        if(serviceMap.containsKey(rpcServiceName)){
            return;
        }
        serviceMap.put(rpcServiceName, rpcService);
        log.info("Add service: {} and interfaces:{}", rpcServiceName, rpcService.getClass().getInterfaces());
    }

    @Override
    public Object getService(String rpcServiceName) {
        Object service = serviceMap.get(rpcServiceName);
        if(null == service){
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }

    @Override
    public void publishService(Object rpcService) {
        try {
            // 注册服务时将Service对象存储于Map中，这样获取时直接去Map中拿
            addService(rpcService);

            // 注册服务
            String host = InetAddress.getLocalHost().getHostAddress();
            String rpcServiceName = rpcService.getClass().getInterfaces()[0].getCanonicalName();
            serviceRegistry.registryService(rpcServiceName, new InetSocketAddress(host, SocketRpcServer.PORT));
        } catch (UnknownHostException e) {
            log.error("getLocalHost()异常", e);
        }
    }
}
