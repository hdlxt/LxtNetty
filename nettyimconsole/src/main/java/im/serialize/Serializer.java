package im.serialize;

import im.serialize.impl.JSONSerializer;

/**
 * @ClassName: Serializer
 * @Author: lxt
 * @Description: 序列化接口
 * @Version: 1.0
 */
public interface Serializer {
    /**
     * json序列化
     */
    byte JSON_SERIEALIZER = 1;
    /**
     * 默认序列化算法
     */
    Serializer DEFAULT = new JSONSerializer();
    /**
     * 序列化算法
     * @return
     */
    byte getSerielizerAlgorithm();

    /**
     * java对象转化为二进制
     * @param object
     * @return
     */
    byte[] seriallize(Object object);

    /**
     * 二进制转化为java对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz,byte[] bytes);
}
