package com.saum.transport.socket;

import com.saum.provider.ServiceProvider;
import com.saum.provider.impl.ServiceProviderImpl;
import com.saum.registry.ServiceRegistry;
import com.saum.registry.zk.ZkServiceRegistryImpl;
import com.saum.utils.ThreadPoolFactoryUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description:
 */
@Slf4j
public class SocketRpcServer {

    public static final int PORT = 9000;

    private final ExecutorService threadPool;
    private final ServiceProvider serviceProvider;

    public SocketRpcServer(){
        threadPool = ThreadPoolFactoryUtils.createCustomThreadPoolIfAbsent("socket-server-rpc-pool");
        serviceProvider = new ServiceProviderImpl();
    }

    /**
    * 注册服务
    */
    public void registerService(Object rpcService){
        serviceProvider.publishService(rpcService);
    }

    public void start(){
        // 1.创建ServerSocket对象并绑定一个端口
        try(ServerSocket server = new ServerSocket()){
            String host = InetAddress.getLocalHost().getHostAddress();
            server.bind(new InetSocketAddress(host, PORT));
            Socket socket;
            // 2.通过accpet()方法监听客户端请求，accept会阻塞，直到收到客户端的连接请求
            while((socket = server.accept()) != null){
                log.info("建立连接成功[{}]", socket.getInetAddress());
                threadPool.execute(new SocketRpcRequestHandleRunnable(socket));
            }
            threadPool.shutdownNow();
        }catch (IOException e){
            log.error("IO异常:", e);
        }
    }
}
