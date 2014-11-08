package com.baidu.harry.rpc.core;

import com.baidu.harry.rpc.core.handler.DefaultRouterHandler;
import com.baidu.harry.rpc.util.ServiceContainer;
import com.baidu.harry.rpc.zookeeper.ZkServer;

import java.io.File;
import java.net.ServerSocket;

public class RomanServerCore {

    public static void init(String service_path, int port) throws Exception {
        ServiceContainer.scanClassPath(new File(service_path));
        new ZkServer().connectZookeeper(port);
        export(port);
    }

    /**
     * 暴露服务
     *
     * @param port 服务端口
     * @throws Exception
     */
    public static void export(final int port) throws Exception {

        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port " + port);
        }

        ServerSocket server = new ServerSocket(port);
        System.out.println("server is starting at port " + port);
        for (; ; ) {
            new DefaultRouterHandler(server.accept()).run();
        }
    }
}
