package com.bkhech.protocol.protocol;

import com.bkhech.protocol.handler.RpcServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty 服务
 *
 * @author guowm
 * @date 2021/9/30
 */
@Slf4j
public class NettyServer {

    //服务地址
    private final String serverAddress;
    //服务端口
    private final int serverPort;

    public NettyServer(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void startNettyServer() {
        log.info("Begin start Netty server");
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new RpcServerInitializer());

        try {
            ChannelFuture future = bootstrap.bind(this.serverAddress, this.serverPort).sync();
            log.info("Server started success on port: {}", this.serverPort);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
