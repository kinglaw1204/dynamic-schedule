package top.luozhou.core.serialize;

/**
 * @description: 序列化接口
 * @author: luozhou
 * @create: 2019-10-02 14:04
 **/
public interface ISerializer<T> {

    /**
     * 序列化成字符串
     *
     * @param t
     * @return
     */
    String toString(T t);

    /**
     * 序列化成对象
     *
     * @param str
     * @return
     */
    T toObj(String str, Class<T> clz);
}
