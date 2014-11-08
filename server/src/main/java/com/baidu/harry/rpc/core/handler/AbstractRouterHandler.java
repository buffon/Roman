package com.baidu.harry.rpc.core.handler;

import java.net.Socket;

/**
 * @author: harry.chen
 * @since: 14-11-7 下午5:30
 */
public abstract class AbstractRouterHandler {

    public abstract void handler(Socket socket) throws Exception;
}
