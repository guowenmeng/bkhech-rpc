package com.bkhech.protocol.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Request Holder 类型
 * @author guowm
 * @date 2021/9/30
 */
public class RequestHolder {

    /**
     * 请求ID
     */
    public static final AtomicLong REQUEST_ID = new AtomicLong();

    /**
     * 请求异步结果集合
     */
    public static final Map<Long, RpcFuture> REQUEST_FUTURE_MAP = new ConcurrentHashMap<>(1024);

}
