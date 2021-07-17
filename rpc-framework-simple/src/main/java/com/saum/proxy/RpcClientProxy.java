package com.saum.proxy;

import com.saum.remoting.dto.RpcRequest;
import com.saum.remoting.dto.RpcResponse;
import com.saum.transport.socket.SocketRpcClient;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description: 动态代理
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    private final SocketRpcClient socketRpcClient;

    public RpcClientProxy(SocketRpcClient socketRpcClient) {
        this.socketRpcClient = socketRpcClient;
    }

    public <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("invoked method: [{}]", method.getName());
        RpcRequest rpcRequest = RpcRequest.builder().methodName(method.getName())
                .parameters(args)
                .paramTypes(method.getParameterTypes())
                .interfaceName(method.getDeclaringClass().getName())
                .requestId(UUID.randomUUID().toString())
                .build();

        RpcResponse<Object> rpcResponse = null;

        Object obj = socketRpcClient.sendRpcRequest(rpcRequest);
        rpcResponse = (RpcResponse<Object>) socketRpcClient.sendRpcRequest(rpcRequest);
        return rpcResponse.getData();
    }
}
