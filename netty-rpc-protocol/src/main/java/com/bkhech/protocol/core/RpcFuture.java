package com.bkhech.protocol.core;

import io.netty.util.concurrent.Promise;
import lombok.Data;

/**
 * 异步返回类
 * @author guowm
 * @date 2021/9/30
 */
@Data
public class RpcFuture<T> {
    private final Promise<T> promise;

    public RpcFuture(Promise<T> promise) {
        this.promise = promise;
    }
}
