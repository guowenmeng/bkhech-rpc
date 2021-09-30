package com.bkhech.protocol.protocol;

import com.bkhech.protocol.core.RpcProtocol;
import com.bkhech.protocol.core.RpcRequest;
import com.bkhech.protocol.handler.RpcClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty 客户端
 * @author guowm
 * @date 2021/9/30
 */
@Slf4j
public class NettyClient {

    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private final String serviceAddress;
    private final int servicePort;

    public NettyClient(String serviceAddress, int servicePort) {
        log.info("Begin init Netty client. ip: {}, port: {}", serviceAddress, servicePort);

        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());
        this.serviceAddress = serviceAddress;
        this.servicePort = servicePort;
    }

    /**
     * 发送数据
     * @param protocol
     * @throws InterruptedException
     */
    public void sendResult(RpcProtocol<RpcRequest> protocol) throws InterruptedException {
        final ChannelFuture channelFuture = bootstrap.connect(this.serviceAddress, this.servicePort).sync();
        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    log.info("connect rpc server {} success.", serviceAddress);
                } else {
                    log.error("connect rpc server {} failed. ", servicePort);
                    future.cause().printStackTrace();
                    eventLoopGroup.shutdownGracefully();
                }
            }
        });
        log.info("begin transfer data");
        channelFuture.channel().writeAndFlush(protocol);
    }
}
