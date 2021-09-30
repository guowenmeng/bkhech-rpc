package com.bkhech.protocol.handler;

import com.bkhech.protocol.codec.RpcDecoder;
import com.bkhech.protocol.codec.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 初始化客户端 pipeline
 * @author guowm
 * @date 2021/9/30
 */
@Slf4j
public class RpcClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
      log.info("Init RpcClientInitializer");
      ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(
              2*1024*1024,
              12,
              4,
              0,
              0))
              .addLast(new LoggingHandler())
              .addLast(new RpcEncoder())
              .addLast(new RpcDecoder())
              .addLast(new RpcClientHandler());
    }
}
