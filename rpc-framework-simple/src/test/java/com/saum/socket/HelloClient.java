package com.saum.socket;

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
public class HelloClient {
    public Object send(Message message, String host, int port){
        try(Socket socket = new Socket(host, port)){
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return objectInputStream.readObject();
        }catch (IOException | ClassNotFoundException e){
            log.error("异常：", e);
        }

        return null;
    }

    public static void main(String[] args) {
        HelloClient helloClient = new HelloClient();
        Message message = (Message) helloClient.send(new Message("hello, I'm client!"), "127.0.0.1", 6666);
        System.out.println("客户端接收到的响应消息：" + message.getContent());
    }
}
