package com.saum.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RpcConfigEnum {

    /**
     * rpc 配置文件名
     */
    RPC_CONFIG_PATH("rpc.properties"),

    /**
     * zookeeper地址
     */
    ZK_ADDRESS("rpc.zookeeper.address");

    private final String value;
}
