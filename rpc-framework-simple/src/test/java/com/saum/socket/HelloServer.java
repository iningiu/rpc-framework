package com.saum.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description:
 */
@Slf4j
public class HelloServer {

    public void start(int port){
        // 1.创建ServerSocket对象并绑定一个端口
        try(ServerSocket server = new ServerSocket(port)){
            Socket socket;
            // 2.通过accpet()方法监听客户端请求，accept会阻塞，直到收到客户端的连接请求
            while((socket = server.accept()) != null){
                log.info("建立连接成功！");
                try(ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream())){
                    // 3.通过输入流读取客户端发送的请求信息
                    Message message = (Message) objectInputStream.readObject();
                    log.info("服务器收到消息：" + message.getContent());
                    message.setContent("hi, I'm server!");
                    // 4.通过输出流向客户端发送响应消息
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();
                }
            }

        }catch (IOException | ClassNotFoundException e){
            log.error("IO异常:", e);
        }
    }

    public static void main(String[] args) {
        HelloServer helloServer = new HelloServer();
        helloServer.start(6666);
    }
}
