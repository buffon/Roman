package com.baidu.harry.rpc.core;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author: harry.chen
 * @since: 14-11-12 下午9:14
 */
public class RomanNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("service", new ServiceParser());
    }
}
