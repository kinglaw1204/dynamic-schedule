package luozhou.top.core.serialize;

import com.alibaba.fastjson.JSON;

/**
 * @description: 默认序列化器
 * @author: luozhou
 * @create: 2019-10-02 14:07
 **/
public class DefaultSerializer<T> implements ISerializer<T> {

    @Override
    public String toString(T t) {
        return JSON.toJSONString(t);
    }


    @Override
    public T toObj(String str, Class<T> clz) {

        return JSON.parseObject(str, clz);
    }
}
