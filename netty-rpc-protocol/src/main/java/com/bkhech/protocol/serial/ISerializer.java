package com.bkhech.protocol.serial;

/**
 * @author guowm
 * @date 2021/9/18
 */
public interface ISerializer {
    /**
     * 序列化
     * @param obj
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T obj);

    /**
     * 反序列化
     * @param data
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] data, Class<T> clazz);

    /**
     * 序列化类型
     * @return
     */
    byte getType();
}
