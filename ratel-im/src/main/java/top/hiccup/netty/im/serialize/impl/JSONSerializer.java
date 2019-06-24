package top.hiccup.netty.im.serialize.impl;

import com.alibaba.fastjson.JSON;
import top.hiccup.netty.im.serialize.Serializer;
import top.hiccup.netty.im.serialize.SerializerAlogrithm;

public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);
    }
}
