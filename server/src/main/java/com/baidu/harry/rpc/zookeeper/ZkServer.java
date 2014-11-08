package com.baidu.harry.rpc.zookeeper;

import com.baidu.harry.rpc.util.SystemUtil;
import org.apache.zookeeper.*;

import static com.baidu.harry.rpc.common.Contants.*;

public class ZkServer {

    /**
     * 连接zookeeper
     */
    public void connectZookeeper(int port) throws Exception {
        String address = SystemUtil.getIp() + ":" + port;
        ZooKeeper zk = new ZooKeeper(ZK_HOST, SESSION_TIMEOUT, new Watcher() {
            public void process(WatchedEvent event) {
                // 不做处理
            }
        });
        // 子节点的类型设置为EPHEMERAL_SEQUENTIAL, 表明这是一个临时节点, 且在子节点的名称后面加上一串数字后缀,将server的地址数据关联到新创建的子节点上
        String createdPath = zk.create(zkDir(), address.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("create: " + createdPath);
    }
}