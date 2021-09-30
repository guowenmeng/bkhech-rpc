package com.bkhech.protocol.serial;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author guowm
 * @date 2021/9/18
 */
public class SerializerManager {

    private static final Map<Byte, ISerializer> serializer = new ConcurrentHashMap<>(2);

    static {
        final JavaSerializer javaSerializer = new JavaSerializer();
        final JsonSerializer jsonSerializer = new JsonSerializer();
        serializer.put(javaSerializer.getType(), javaSerializer);
        serializer.put(jsonSerializer.getType(), jsonSerializer);
    }


    public static ISerializer getSerializer(byte key) {
        final ISerializer serializer = SerializerManager.serializer.get(key);
        if (serializer == null) {
            // 返回默认序列化器
            return new JavaSerializer();
        }
        return serializer;
    }

}
