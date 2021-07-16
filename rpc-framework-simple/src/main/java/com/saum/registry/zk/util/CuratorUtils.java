package com.saum.registry.zk.util;

import com.saum.enums.RpcConfigEnum;
import com.saum.utils.PropertiesFileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CuratorUtils {

    public static final String ZK_REGISTER_ROOT_PATH = "/saum-rpc";

    private static final int BASE_SLEEP_TIME = 1000;
    // 最大重试次数
    private static final int MAX_RETRIES = 3;
    // zookeeper默认连接地址
    private static final String DEFAULT_ZOOKEEPER_ADDRESS = "127.0.0.1:2181";
    // 保存已经创建的持久化节点
    private static final Set<String> REGISTERED_PATH_SET = ConcurrentHashMap.newKeySet();

    private static CuratorFramework zkClient;

    private CuratorUtils(){
    }

    public static CuratorFramework getZkClient(){
        Properties properties = PropertiesFileUtil.readPropertiesFile(RpcConfigEnum.RPC_CONFIG_PATH.getValue());
        String zkAddress;
        if(properties != null){
            String property = properties.getProperty(RpcConfigEnum.ZK_ADDRESS.getValue());
            if(property != null) zkAddress = property;
            else zkAddress = DEFAULT_ZOOKEEPER_ADDRESS;
        }else{
            zkAddress = DEFAULT_ZOOKEEPER_ADDRESS;
        }

        if(zkClient != null && zkClient.getState() == CuratorFrameworkState.STARTED){
            return zkClient;
        }

        /**
         * 先给定一个初始化sleep时间baseSleepTimeMs，在此基础上结合重试次数，通过以下代码计算当前需要的sleep时间
         * long sleepMs = baseSleepTimeMs * Math.max(1, random.nextInt(1 << (retryCount + 1)));
         * if (sleepMs > maxSleepMs ){
         *     sleepMs = maxSleepMs;
         * }
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
        zkClient = CuratorFrameworkFactory.newClient(zkAddress, retryPolicy);
        zkClient.start();

        try {
            // 阻塞，直到与ZooKeeper的连接可用（返回true）或已超过maxWaitTime（返回false）
            if(!zkClient.blockUntilConnected(30, TimeUnit.SECONDS)){
                throw new RuntimeException("zookeeper连接超时！");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zkClient;
    }

    /**
     * 创建持久化节点
     * @param path 节点路径
     */
    public static void createPersistentNode(CuratorFramework zkClient, String path){
        try {
            // 先去hashset中查看该节点是否存在，如果存在就不必再连接zookeeper去查了
            if(REGISTERED_PATH_SET.contains(path) || zkClient.checkExists().forPath(path) != null){
                log.info("节点[{}]已经存在！", path);
            }else{
                zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
                log.info("节点[{}]创建成功！", path);
            }
            REGISTERED_PATH_SET.add(path);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("节点[{}]创建失败！", path);
        }
    }
}
