package com.saum.loadbalance;

import com.saum.remoting.dto.RpcRequest;

import java.util.List;

/**
 * @Author saum
 * @Date 2021/7/18
 * @Description: 负载均衡策略接口
 */
public interface LoadBalance {
    String selectServiceAddress(List<String> serviceAddressList, RpcRequest rpcRequest);
}
