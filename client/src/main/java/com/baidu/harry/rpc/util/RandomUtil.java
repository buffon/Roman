package com.baidu.harry.rpc.util;

import java.util.Random;


public class RandomUtil {

    public static Integer getRandomInt(Integer no) {
        Random random = new Random();
        return random.nextInt(no);
    }
}
