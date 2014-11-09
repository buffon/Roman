package com.baidu.harry.rpc.strategy;

import com.baidu.harry.rpc.util.RandomUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.List;

/**
 * 负载均衡策略
 */
public class RandomAccessServiceFound implements IServiceFound {

    @Override
    public MutablePair<String, Integer> getServerHost(List<String> list) {
        if (CollectionUtils.isEmpty(list)){
             throw new RuntimeException("No Server Exists.");
        }
        int offset = RandomUtil.getRandomInt(list.size());
        System.out.println("offsset " + offset);
        String value = list.get(offset);
        String[] temp = value.split(":");
        return new MutablePair<String, Integer>(temp[0], Integer.parseInt(temp[1]));
    }
}
