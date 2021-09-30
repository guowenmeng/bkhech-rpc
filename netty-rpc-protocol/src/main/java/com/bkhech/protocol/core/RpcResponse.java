package com.bkhech.protocol.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author guowm
 * @date 2021/9/18
 */
@Data
public class RpcResponse implements Serializable {
    private Object data;
    private String msg;
}
