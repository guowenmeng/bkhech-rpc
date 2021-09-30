package com.bkhech.protocol.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author guowm
 * @date 2021/9/18
 */
@Data
public class RpcProtocol<T> implements Serializable {
    private Header header;
    private T content;
}
