package com.saum.transport.socket;

import com.saum.factory.SingletonFactory;
import com.saum.remoting.dto.RpcRequest;
import com.saum.remoting.dto.RpcResponse;
import com.saum.remoting.handler.RpcRequestHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description:
 */
@Slf4j
public class SocketRpcRequestHandleRunnable implements Runnable {
    private final Socket socket;
    private final RpcRequestHandler rpcRequestHandler;

    public SocketRpcRequestHandleRunnable(Socket socket) {
        this.socket = socket;
        // 必须是单例的
        this.rpcRequestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }

    @Override
    public void run() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())){
            // 3.通过输入流读取客户端发送的请求信息
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            // 4.服务端调用相关方法
            Object result = rpcRequestHandler.handle(rpcRequest);

            // 5.通过输出流向客户端发送响应消息
            objectOutputStream.writeObject(RpcResponse.success(result, rpcRequest.getRequestId()));
            objectOutputStream.flush();
        }catch (IOException | ClassNotFoundException e){
            log.error("发生异常:", e);
        }
    }
}
