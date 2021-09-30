package com.bkhech.protocol.handler;

import com.bkhech.protocol.constants.ReqType;
import com.bkhech.protocol.core.Header;
import com.bkhech.protocol.core.RpcProtocol;
import com.bkhech.protocol.core.RpcRequest;
import com.bkhech.protocol.core.RpcResponse;
import com.bkhech.protocol.spring.SpringBeanManager;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 服务端业务处理器
 *
 * @author guowm
 * @date 2021/9/30
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcRequest>> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcProtocol<RpcRequest> msg) throws Exception {
        // 通过解码器解码，已经将结果解码成了 RpcProtocol<RpcRequest> 对象

        // 实际业务处理
        Object result = invoke(msg.getContent());

        //响应内容
        RpcResponse response = new RpcResponse();
        response.setData(result);
        response.setMsg("success");
        //响应头
        final Header header = msg.getHeader();
        //将请求类型设置为响应标志
        header.setReqType(ReqType.RESPONSE.code());
        // 消息协议：响应客户端对象
        RpcProtocol<RpcResponse> resProtocol = new RpcProtocol<>();
        resProtocol.setHeader(header);
        resProtocol.setContent(response);

        //将消息写给客户端
        ctx.writeAndFlush(resProtocol);
    }

    /**
     * 实际业务处理
     *
     * @param request
     * @return
     */
    private Object invoke(RpcRequest request) {
        try {
            Class<?> clazz = Class.forName(request.getClassName());
            Object bean = SpringBeanManager.getBean(clazz);
            //只包含本类声明的方法，注意：getMethod 包含父类在内的 public 方法。
            Method method = clazz.getDeclaredMethod(request.getMethodName(), request.getParameterTypes());
            return method.invoke(bean, request.getParams());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
