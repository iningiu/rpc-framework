package com.saum.transport.socket;

import com.saum.exception.RpcException;
import com.saum.registry.ServiceDiscovery;
import com.saum.registry.zk.ZkServiceDiscoveryImpl;
import com.saum.remoting.dto.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description:
 */
public class SocketRpcClient {
    private final ServiceDiscovery serviceDiscovery;

    public SocketRpcClient() {
        this.serviceDiscovery = new ZkServiceDiscoveryImpl(loadBalance);
    }

    /**
    * @Description 发送rpc请求并返回响应结果
    */
    public Object sendRpcRequest(RpcRequest rpcRequest){
        // 获取请求服务的socket地址
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest);
        try(Socket socket = new Socket()){
            socket.connect(inetSocketAddress);
            // 通过输出流向服务端发送rpc请求
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);

            // 通过输入流获取rpc响应
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            throw new RpcException("调用服务失败：", e);
        }
    }
}
