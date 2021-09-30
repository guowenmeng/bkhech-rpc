package com.bkhech.protocol.core;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author guowm
 * @date 2021/9/18
 */
@AllArgsConstructor
@Data
public class Header implements Serializable {
    /**
     * 魔数 2字节
     */
    private short magic;
    /**
     * 序列化类型 1字节
     */
    private byte serialType;
    /**
     * 消息类型 1字节
     */
    private byte reqType;
    /**
     * 请求id 8字节
     */
    private long requestId;
    /**
     * 消息体长度 4 字节
     */
    private int length;

    public Header(short magic, byte serialType, byte reqType, long requestId) {
        this.magic = magic;
        this.serialType = serialType;
        this.reqType = reqType;
        this.requestId = requestId;
    }
}
