package com.baidu.harry.rpc.starter;

import com.baidu.harry.rpc.core.RomanServerCore;

/**
 * @author: harry.chen
 * @since: 14-11-7 下午6:01
 */
public class Starter {

    public static void main(String[] args) throws Exception {
        RomanServerCore.init(System.getProperty("user.dir") + "/server-demo/target/classes", 8087);
    }
}
