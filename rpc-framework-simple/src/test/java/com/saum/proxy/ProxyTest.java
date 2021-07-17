package com.saum.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Author saum
 * @Date 2021/7/17
 * @Description:
 */
public class ProxyTest {
    public static void main(String[] args) {
        // 要代理的真实对象
        People people = new Teacher();
        WorkHandler handler = new WorkHandler(people);

        Teacher teacher = new Teacher();
        System.out.println(teacher.getClass());
        System.out.println(Teacher.class);

        System.out.println(people.getClass());
        System.out.println(People.class);

        People proxy = handler.getProxy(People.class);
        /**
         * 通过Proxy类的newProxyInstance方法创建代理对象
         * 第一个参数：people.getClass().getClassLoader()，使用handler对象的classloader对象来加载我们的代理对象
         * 第二个参数：people.getClass().getInterfaces()，这里为代理类提供的接口是真实对象实现的接口，这样代理对象就能像真实对象一样调用接口中的所有方法
         * 第三个参数：handler，我们将代理对象关联到上面的InvocationHandler对象上
         */
//        People proxy = (People) Proxy.newProxyInstance(handler.getClass().getClassLoader(), people.getClass().getInterfaces(), handler);
        System.out.println(proxy.work());
    }
}
