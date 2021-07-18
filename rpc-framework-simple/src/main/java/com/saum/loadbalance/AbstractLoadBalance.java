package com.saum.loadbalance;

import com.saum.remoting.dto.RpcRequest;

import java.util.List;

/**
 * @Author saum
 * @Date 2021/7/18
 * @Description:
 */
public abstract class AbstractLoadBalance implements LoadBalance {
    @Override
    public String selectServiceAddress(List<String> serviceAddressList, RpcRequest rpcRequest) {
        if(serviceAddressList == null || serviceAddressList.size() == 0){
            return null;
        }
        if(serviceAddressList.size() == 1){
            return serviceAddressList.get(0);
        }
        return doSelect(serviceAddressList, rpcRequest);
    }

    protected abstract String doSelect(List<String> serviceAddressList, RpcRequest rpcRequest);
}
