package luozhou.top.core.persistence.service;

import luozhou.top.core.AbstractJob;

import java.util.List;

/**
 * @description: 持久化service
 * @author: luozhou
 * @create: 2019-10-16 15:58
 **/
public interface JobPersistenceService {

    /** 插入job到数据库
     * @param job,clz
     * @return
     */
    boolean insert(AbstractJob job,Class cls);

    /** 根据job信息更新
     * @param job
     * @return
     */
    boolean update(AbstractJob job);

    /** 根据job信息删除
     * @param job
     * @return
     */
    boolean delete (AbstractJob job);

    /** 查询所有未完成的job
     * @return
     */
    List<AbstractJob> queryJobWithNotDone();
}
