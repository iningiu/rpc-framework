package com.saum.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description: 代理类
 */
public class WorkHandler implements InvocationHandler {

    // 代理类中的真实对象
    private Object obj;

    public WorkHandler() {}

    public WorkHandler(Object obj) {
        this.obj = obj;
    }

    public <T> T getProxy(Class<T> clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在真实的对象执行之前我们可以添加自己的操作
        System.out.println("before invoke...");
        Object invoke = method.invoke(obj, args);

        // 在真实的对象执行之后我们可以添加自己的操作
        System.out.println("after invoke...");
        return invoke;
    }
}
