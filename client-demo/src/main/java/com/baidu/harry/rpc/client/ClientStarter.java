package com.baidu.harry.rpc.client;

import com.baidu.harry.rpc.core.RomanClientCore;
import com.baidu.harry.rpc.service.HelloService;

/**
 * @author: harry.chen
 * @since: 14-11-7 下午6:16
 */
public class ClientStarter {

    public static void main(String[] args) throws Exception{
        HelloService service = RomanClientCore.refer(HelloService.class);
        for (int i = 0; i < 10; i++) {
            String hello = service.hello("World" + i);
            System.out.println(hello);
            Thread.sleep(1000);
        }
    }
}
