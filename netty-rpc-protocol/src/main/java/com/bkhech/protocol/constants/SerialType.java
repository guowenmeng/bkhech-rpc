package com.bkhech.protocol.constants;

/**
 * @author guowm
 * @date 2021/9/18
 */
public enum SerialType {
    JSON_SERIAL((byte)1),
    JAVA_SERIAL((byte)2);

    private byte code;

    SerialType(byte code){
        this.code=code;
    }

    public byte code(){
        return this.code;
    }
}
