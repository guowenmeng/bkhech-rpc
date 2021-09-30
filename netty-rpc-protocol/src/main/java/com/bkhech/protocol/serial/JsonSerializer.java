package com.bkhech.protocol.serial;

import com.alibaba.fastjson.JSON;
import com.bkhech.protocol.constants.SerialType;

/**
 * @author guowm
 * @date 2021/9/18
 */
public class JsonSerializer implements ISerializer{
    /**
     * 序列化
     *
     * @param obj
     * @return
     */
    @Override
    public <T> byte[] serialize(T obj) {
        return JSON.toJSONBytes(obj);
    }

    /**
     * 反序列化
     *
     * @param data
     * @param clazz
     * @return
     */
    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data), clazz);
    }

    /**
     * 序列化类型
     *
     * @return
     */
    @Override
    public byte getType() {
        return SerialType.JSON_SERIAL.code();
    }
}
