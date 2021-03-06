package com.saum.registry.zk;

import com.saum.enums.RpcErrorMessageEnum;
import com.saum.exception.RpcException;
import com.saum.loadbalance.LoadBalance;
import com.saum.loadbalance.impl.RandomLoadBalance;
import com.saum.registry.ServiceDiscovery;
import com.saum.registry.zk.util.CuratorUtils;
import com.saum.remoting.dto.RpcRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;

@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {
    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl() {
        this.loadBalance = new RandomLoadBalance();
    }

    @Override
    public InetSocketAddress lookupService(RpcRequest rpcRequest) {
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildRenNodes(zkClient, rpcServiceName);
        if(serviceUrlList == null || serviceUrlList.size() == 0){
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }

        // 负载均衡
        String serviceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcRequest); // 格式：127.0.0.1:8000
        log.info("成功找到服务地址：[{}]", serviceUrl);

        String[] socketAddressArray = serviceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
