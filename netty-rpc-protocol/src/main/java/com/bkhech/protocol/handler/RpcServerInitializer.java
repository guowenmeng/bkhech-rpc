package com.bkhech.protocol.handler;

import com.bkhech.protocol.codec.RpcDecoder;
import com.bkhech.protocol.codec.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 初始化 channel pipeline
 *
 * @author guowm
 * @date 2021/9/30
 * @see LengthFieldBasedFrameDecoder 解释：{@literal https://blog.csdn.net/liyantianmin/article/details/85603347}
 */
public class RpcServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().
                //一个解码器（自定义长度解码器）：自定义长度解决TCP拆包黏包问题
                //划重点: 参照一个公式写，肯定没问题:
                //公式: 发送数据包长度 = 长度域的值 + lengthFieldOffset + lengthFieldLength + lengthAdjustment
                        addLast(new LengthFieldBasedFrameDecoder(
                        // 发送的数据帧最大长度
                        10 * 1024 * 1024,

                        // magic(2) + serialType(1) + reqType(1) + requestId(8)
                        // 定义长度域位于发送的字节数组中的下标。换句话说：发送的字节数组中
                        // 下标为${lengthFieldOffset}的地方是长度域的开始地方
                        12,

                        // length(4)
                        // 用于描述定义的长度域的长度。换句话说：发送字节数组bytes时, 字节数组
                        // bytes[lengthFieldOffset, lengthFieldOffset+lengthFieldLength]域对应于的定义长度域部分
                        4,

                        // 满足公式: 发送的字节数组bytes.length - lengthFieldLength = bytes[lengthFieldOffset, lengthFieldOffset+lengthFieldLength] + lengthFieldOffset + lengthAdjustment
                        0,
                        // - 接收到的发送数据包，去除前initialBytesToStrip位
                        0))
                //解码器
                .addLast(new RpcDecoder())
                //编码器
                .addLast(new RpcEncoder())
                //业务处理器
                .addLast(new RpcServerHandler());


    }
}
