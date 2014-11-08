package com.baidu.harry.rpc.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ZkClient {

    private String groupNode = "roman";
    private ZooKeeper zk;
    private Stat stat = new Stat();
    private volatile List<String> serverList;

    public List<String> getServerList() {
        return this.serverList;
    }

    private CountDownLatch connectedSignal = new CountDownLatch(1);

    /**
     * 连接zookeeper
     */
    public void connectZookeeper() throws Exception {
        zk = new ZooKeeper("127.0.0.1", 5000, new Watcher() {
            public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                    connectedSignal.countDown();// 倒数-1
                }
                if (event.getType() == Event.EventType.NodeChildrenChanged
                        && ("/" + groupNode).equals(event.getPath())) {
                    try {
                        updateServerList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        connectedSignal.await(); // 等待连接完成
        updateServerList();
    }

    /**
     * 更新server列表
     */
    private void updateServerList() throws Exception {
        List<String> newServerList = new ArrayList<String>();

        // watch参数为true, 表示监听子节点变化事件. 每次都需要重新注册监听, 因为一次注册, 只能监听一次事件, 如果还想继续保持监听, 必须重新注册
        List<String> subList = zk.getChildren("/" + groupNode, true);
        for (String subNode : subList) {
            // 获取每个子节点下关联的server地址
            byte[] data = zk.getData("/" + groupNode + "/" + subNode, false, stat);
            newServerList.add(new String(data, "utf-8"));
        }

        // 替换server列表
        serverList = newServerList;

        System.out.println("server list updated: " + serverList);
    }

    public void init() throws Exception {
        connectZookeeper();
    }
}