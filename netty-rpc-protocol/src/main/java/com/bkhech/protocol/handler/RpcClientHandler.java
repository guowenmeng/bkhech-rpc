package com.bkhech.protocol.handler;

import com.bkhech.protocol.core.RequestHolder;
import com.bkhech.protocol.core.RpcFuture;
import com.bkhech.protocol.core.RpcProtocol;
import com.bkhech.protocol.core.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端业务处理器
 * @author guowm
 * @date 2021/9/30
 */
@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcResponse> msg) throws Exception {
        log.info("Receive Rpc Server Result");
        final long requestId = msg.getHeader().getRequestId();
        final RpcFuture future = RequestHolder.REQUEST_FUTURE_MAP.remove(requestId);
        //设置返回结果
        future.getPromise().setSuccess(msg.getContent());
    }
}
