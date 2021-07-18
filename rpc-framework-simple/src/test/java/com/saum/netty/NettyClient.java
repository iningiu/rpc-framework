package com.saum.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author saum
 * @Date 2021/7/18
 * @Description: 客户端启动类
 */
public class NettyClient {
    public static void main(String[] args) throws Exception {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            //创建bootstrap对象，配置参数
            Bootstrap bootstrap = new Bootstrap();
            // 设置线程组
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 添加客户端通道的处理器
                            socketChannel.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            System.out.println("客户端准备就绪...");
            // 连接服务端，ChannelFuture提供操作完成时一种异步通知的方式，不会像Socket一样阻塞，类似观察者模式
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6666).sync();
            // 添加监听器
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    // 判断是否操作成功
                    if(channelFuture.isSuccess()){
                        System.out.println("连接成功！");
                    }else{
                        System.out.println("连接失败！");
                    }
                }
            });
            // 对通道关闭进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 关闭线程组
            eventExecutors.shutdownGracefully();
        }
    }
}
