package com.baidu.harry.rpc.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by chenyehui on 14-11-8.
 */
public class SystemUtil {

    public static String getIp(){
        String ip = null;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {

        }
        return ip;
    }
}
