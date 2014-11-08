package com.baidu.harry.rpc.zookeeper;

import org.apache.zookeeper.*;

public class ZkServer {

    private String groupNode = "roman";
    private String subNode = "sub";

    private static final String ZK_HOST = "127.0.0.1";
    private static final Integer SESSION_TIMEOUT = 5000;

    /**
     * 连接zookeeper
     */
    public void connectZookeeper(int port) throws Exception {
        ZooKeeper zk = new ZooKeeper(ZK_HOST, SESSION_TIMEOUT, new Watcher() {
            public void process(WatchedEvent event) {
                // 不做处理
            }
        });
        // 子节点的类型设置为EPHEMERAL_SEQUENTIAL, 表明这是一个临时节点, 且在子节点的名称后面加上一串数字后缀,将server的地址数据关联到新创建的子节点上
        String createdPath = zk.create("/" + groupNode + "/" + subNode, ("localhost:" + port).getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("create: " + createdPath);
    }

    public void handle() throws InterruptedException {
        Thread.sleep(Long.MAX_VALUE);
    }

    public void init(int port) throws Exception {
        ZkServer as = new ZkServer();
        as.connectZookeeper(port);
        // as.handle();
    }
}