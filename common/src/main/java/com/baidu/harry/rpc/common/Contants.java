package com.baidu.harry.rpc.common;

/**
 * Created by chenyehui on 14-11-8.
 */
public class Contants {

    /**
     * zookeeper 目录
     */
    public static final String groupNode = "roman";
    public static final String subNode = "sub";

    public static final Integer SESSION_TIMEOUT = 5000;

    /**
     * zookeeper 地址
     */
    public static final String ZK_HOST = "127.0.0.1";


    public static String zkDir(){
        return "/" + groupNode + "/" + subNode;
    }

    public static final String UTF8 = "UTF-8";

}
