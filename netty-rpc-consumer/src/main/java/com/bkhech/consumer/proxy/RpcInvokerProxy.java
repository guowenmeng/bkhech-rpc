package com.bkhech.consumer.proxy;

import com.bkhech.protocol.constants.ReqType;
import com.bkhech.protocol.constants.RpcConstant;
import com.bkhech.protocol.constants.SerialType;
import com.bkhech.protocol.core.*;
import com.bkhech.protocol.protocol.NettyClient;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 客户端代理处理类
 * @author guowm
 * @date 2021/9/30
 */
@Slf4j
public class RpcInvokerProxy implements InvocationHandler {
    private String host;
    private int port;

    public RpcInvokerProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("begin invoke target server");

        //请求头
        final long requestId = RequestHolder.REQUEST_ID.incrementAndGet();
        Header header = new Header(
                RpcConstant.MAGIC,
                SerialType.JAVA_SERIAL.code(),
                ReqType.REQUEST.code(),
                requestId);
        //请求内容
        RpcRequest request = new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParams(args);
        // 消息协议：请求服务端对象
        RpcProtocol<RpcRequest> reqProtocol = new RpcProtocol<>();
        reqProtocol.setHeader(header);
        reqProtocol.setContent(request);

        //获取 netty client
        NettyClient nettyClient = new NettyClient(this.host, this.port);
        //准备异步对象
        RpcFuture<RpcResponse> future = new RpcFuture<>(new DefaultPromise<RpcResponse>(new DefaultEventLoop()));
        RequestHolder.REQUEST_FUTURE_MAP.put(requestId, future);
        //执行
        nettyClient.sendResult(reqProtocol);
        //同步转异步
        return future.getPromise().get().getData();
    }
}
