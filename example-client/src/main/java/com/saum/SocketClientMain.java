package com.saum;

import com.saum.proxy.RpcClientProxy;
import com.saum.transport.socket.SocketRpcClient;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description:
 */
public class SocketClientMain {
    public static void main(String[] args) {
        SocketRpcClient socketRpcClient = new SocketRpcClient();

        RpcClientProxy rpcClientProxy = new RpcClientProxy(socketRpcClient);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);

        Hello hello = new Hello("111", "222");
        String result = helloService.hello(hello);
        System.out.println(result);
    }
}
