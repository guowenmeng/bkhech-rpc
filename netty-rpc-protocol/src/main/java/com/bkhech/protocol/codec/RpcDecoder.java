package com.bkhech.protocol.codec;

import com.bkhech.protocol.constants.ReqType;
import com.bkhech.protocol.constants.RpcConstant;
import com.bkhech.protocol.core.Header;
import com.bkhech.protocol.core.RpcProtocol;
import com.bkhech.protocol.core.RpcRequest;
import com.bkhech.protocol.core.RpcResponse;
import com.bkhech.protocol.serial.ISerializer;
import com.bkhech.protocol.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author guowm
 * @date 2021/9/18
 */
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.info("========begin RpcDecoder==========");
        if (in.readableBytes() < RpcConstant.HEAD_TOTAL_LEN) {
            return;
        }

        //标记读取开始索引
        in.markReaderIndex();
        //读取2个字节的magic
        short magic = in.readShort();
        if (magic != RpcConstant.MAGIC) {
            throw new IllegalArgumentException("Illegal request parameter 'magic' " + magic);
        }
        //读取一个字节的序列化类型
        byte serialType = in.readByte();
        //读取一个字节的消息类型
        byte reqType = in.readByte();
        //读取请求ID
        long requestId = in.readLong();
        //读取数据报文长度
        int dataLength = in.readInt();

        //in.readableBytes() 剩余的可读字节
        //如果剩余的可读字节小于实际的数据长度，则直接返回
        if (in.readableBytes() < dataLength) {
            //重置读索引
            in.resetReaderIndex();
            return;
        }
        //读取消息体的内容
        byte[] content = new byte[dataLength];
        in.readBytes(content);

        Header header = new Header(magic, serialType, reqType, requestId, dataLength);
        //根据序列化类型获取序列化器
        ISerializer serializer = SerializerManager.getSerializer(serialType);
        //获取请求类型
        ReqType rt = ReqType.findByCode(reqType);
        switch (rt) {
            case REQUEST:
                //序列化消息内容
                RpcRequest request = serializer.deserialize(content, RpcRequest.class);
                //构建消息协议
                final RpcProtocol<RpcRequest> reqProtocol = new RpcProtocol<>();
                reqProtocol.setHeader(header);
                reqProtocol.setContent(request);
                //写出，并往下传递
                out.add(reqProtocol);
                break;
            case RESPONSE:
                RpcResponse response = serializer.deserialize(content, RpcResponse.class);
                RpcProtocol<RpcResponse> resProtocol = new RpcProtocol<>();
                resProtocol.setHeader(header);
                resProtocol.setContent(response);
                out.add(resProtocol);
                break;
            case HEARTBEAT:
                //TODO
                break;
            default:
        }

    }
}
