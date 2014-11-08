package com.baidu.harry.rpc.strategy;

import org.apache.commons.lang3.tuple.MutablePair;

import java.util.List;


public interface IServiceFound {

    public MutablePair<String, Integer> getServerHost(List<String> list);
}
