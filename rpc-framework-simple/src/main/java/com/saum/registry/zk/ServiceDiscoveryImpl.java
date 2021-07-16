package com.saum.registry.zk;

import com.saum.registry.ServiceDiscovery;
import com.saum.registry.zk.util.CuratorUtils;
import com.saum.remoting.dto.RpcRequest;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;

public class ServiceDiscoveryImpl implements ServiceDiscovery {
    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();

        return null;
    }
}
