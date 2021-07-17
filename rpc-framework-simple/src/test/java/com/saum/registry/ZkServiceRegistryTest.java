package com.saum.registry;

import com.saum.registry.ServiceDiscovery;
import com.saum.registry.ServiceRegistry;
import com.saum.registry.zk.ZkServiceDiscoveryImpl;
import com.saum.registry.zk.ZkServiceRegistryImpl;
import com.saum.remoting.dto.RpcRequest;
import com.saum.service.DemoRpcService;
import com.saum.service.DemoRpcServiceImpl;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.UUID;

public class ZkServiceRegistryTest {

    @Test
    public void test(){
        ServiceRegistry zkServiceRegistry = new ZkServiceRegistryImpl();
        InetSocketAddress inetSocketAddress1 = new InetSocketAddress("127.0.0.1", 8000);

        DemoRpcService demoRpcService = new DemoRpcServiceImpl();
        String serviceName = demoRpcService.getClass().getInterfaces()[0].getCanonicalName();
        // 服务注册
        zkServiceRegistry.registryService(serviceName, inetSocketAddress1);
        RpcRequest rpcRequest = RpcRequest.builder()
                .interfaceName(serviceName)
                .requestId(UUID.randomUUID().toString())
                .build();
        ServiceDiscovery zkServiceDiscovery = new ZkServiceDiscoveryImpl();
        InetSocketAddress inetSocketAddress2 = zkServiceDiscovery.lookupService(rpcRequest);

        System.out.println(inetSocketAddress1);
        System.out.println(inetSocketAddress2);
    }
}
