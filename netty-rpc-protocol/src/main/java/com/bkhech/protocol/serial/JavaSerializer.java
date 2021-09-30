package com.bkhech.protocol.serial;

import com.bkhech.protocol.constants.SerialType;

import java.io.*;

/**
 * @author guowm
 * @date 2021/9/18
 */
public class JavaSerializer implements ISerializer {
    /**
     * 序列化
     *
     * @param obj
     * @return
     */
    @Override
    public <T> byte[] serialize(T obj) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try (final ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            //将当前对象序列化到字节流中
            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
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
        try (final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 序列化类型
     *
     * @return
     */
    @Override
    public byte getType() {
        return SerialType.JAVA_SERIAL.code();
    }
}
