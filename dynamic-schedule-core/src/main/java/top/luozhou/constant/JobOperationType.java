package top.luozhou.constant;

import lombok.Getter;

/**
 * @description: job持久化操作类型
 * @author: luozhou
 * @create: 2019-11-22 09:48
 **/
@Getter
public enum JobOperationType {
    /**删除*/
    DELETE,
    /**更新**/
    UPDATE,
    /**插入*/
    INSERT;

}
