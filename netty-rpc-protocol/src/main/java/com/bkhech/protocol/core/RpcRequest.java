package com.bkhech.protocol.core;

import lombok.Data;

import java.io.Serializable;

/**
 * @author guowm
 * @date 2021/9/18
 */
@Data
public class RpcRequest implements Serializable {
    /**
     * 类名
     */
    private String className;
    /**
     * 请求目标方法
     */
    private String methodName;
    /**
     * 请求参数
     */
    private Object[] params;
    /**
     * 请求参数类型
     */
    private Class<?>[] parameterTypes;
}
