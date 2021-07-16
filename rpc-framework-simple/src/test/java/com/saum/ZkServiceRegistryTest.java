package com.saum;

import com.saum.registry.ServiceRegistry;
import com.saum.registry.zk.ZkServiceRegistryImpl;
import com.saum.service.DemoRpcService;
import com.saum.service.DemoRpcServiceImpl;
import org.junit.Test;

import java.net.InetSocketAddress;

public class ZkServiceRegistryTest {

    @Test
    public void test(){
        ServiceRegistry zkServiceRegistry = new ZkServiceRegistryImpl();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8000);

        DemoRpcService demoRpcService = new DemoRpcServiceImpl();
        String serviceName = demoRpcService.getClass().getInterfaces()[0].getCanonicalName();
        // 服务注册
        zkServiceRegistry.registryService(serviceName, inetSocketAddress);

    }
}
