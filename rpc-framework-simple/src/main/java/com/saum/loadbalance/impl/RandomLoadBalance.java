package com.saum.loadbalance.impl;

import com.saum.loadbalance.AbstractLoadBalance;
import com.saum.remoting.dto.RpcRequest;

import java.util.List;
import java.util.Random;

/**
 * @Author saum
 * @Date 2021/7/18
 * @Description: 随机负载均衡策略
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    @Override
    protected String doSelect(List<String> serviceAddressList, RpcRequest rpcRequest) {
        Random random = new Random();
        return serviceAddressList.get(random.nextInt(serviceAddressList.size()));
    }
}
