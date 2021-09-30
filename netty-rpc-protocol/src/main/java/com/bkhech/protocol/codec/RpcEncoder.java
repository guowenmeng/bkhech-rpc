package com.bkhech.protocol.codec;

import com.bkhech.protocol.core.Header;
import com.bkhech.protocol.core.RpcProtocol;
import com.bkhech.protocol.serial.ISerializer;
import com.bkhech.protocol.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author guowm
 * @date 2021/9/18
 */
@Slf4j
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, RpcProtocol<Object> msg, ByteBuf out) throws Exception {
        log.info("===================begin RpcEncoder====================");
        final Header header = msg.getHeader();
        out.writeShort(header.getMagic());
        out.writeByte(header.getSerialType());
        out.writeByte(header.getReqType());
        out.writeLong(header.getRequestId());
        final ISerializer serializer = SerializerManager.getSerializer(header.getSerialType());
        final byte[] data = serializer.serialize(msg.getContent());
        //在编码器中设置消息体长度(也只有在这个时候才知道消息体字节长度，因为和序列化器有关，不同序列化器序列化出来的长度不同)
        out.writeInt(data.length);
        out.writeBytes(data);
    }
}
