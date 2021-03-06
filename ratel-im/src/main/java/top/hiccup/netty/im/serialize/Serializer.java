package top.hiccup.netty.im.serialize;

import top.hiccup.netty.im.serialize.impl.JSONSerializer;

public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlogrithm();

    /**
     * 将Java对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成Java对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
