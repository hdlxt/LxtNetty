package im.serialize.impl;

import com.alibaba.fastjson.JSON;
import im.serialize.Serializer;
import im.serialize.SerializerAlgorithm;

/**
 * @ClassName: JSONSerializer
 * @Author: lxt
 * @Description: json序列化算法
 * @Version: 1.0
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerielizerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }
    @Override
    public byte[] seriallize(Object object) {
        return JSON.toJSONBytes(object);
    }
    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
