package com.saum;

import com.saum.service.impl.HelloServiceImpl;
import com.saum.transport.socket.SocketRpcServer;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description: socket-rpc 服务端
 */
@Slf4j
public class SocketServerMain {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        SocketRpcServer socketRpcServer = new SocketRpcServer();
        socketRpcServer.registerService(helloService);

        socketRpcServer.start();
    }
}
