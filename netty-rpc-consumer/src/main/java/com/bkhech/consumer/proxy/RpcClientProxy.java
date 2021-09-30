package com.bkhech.consumer.proxy;

import java.lang.reflect.Proxy;

/**
 * 客户端代理类，基于JDK Proxy
 * @author guowm
 * @date 2021/9/30
 */
public class RpcClientProxy {

    /**
     * 获取代理类
     * @param interfaceClazz 要代理的接口类
     * @param host 服务器ip
     * @param port 服务器端口
     * @param <T>
     * @return
     */
    public <T> T createProxy(final Class<T> interfaceClazz, final String host, final int port) {
        return (T) Proxy.newProxyInstance(
                interfaceClazz.getClassLoader(),
                new Class[] {interfaceClazz},
                new RpcInvokerProxy(host, port)
        );
    }
}
