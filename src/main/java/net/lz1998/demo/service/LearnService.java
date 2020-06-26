package net.lz1998.demo.service;


import net.lz1998.demo.entity.Learn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LearnService {
    /**
     * 获取回答内容
     *
     * @param groupId 群号
     * @param ask     问内容
     * @return 回答内容
     */
    String getAnswer(Long groupId, String ask);

    /**
     * 设置回答内容
     *
     * @param groupId 群号
     * @param ask     问内容
     * @param answer  回答内容
     * @param adminId 操作管理员QQ
     */
    void setAnswer(Long groupId, String ask, String answer, Long adminId);

    /**
     * 清空学习内容
     *
     * @param groupId 群号
     */
    void clear(Long groupId);

    /**
     * 查询一页学习内容
     *
     * @param groupId  群号
     * @param pageable 页信息
     * @return 一页学习内容
     */
    Page<Learn> getGroupLearnPage(Long groupId, Pageable pageable);

    /**
     * 删除学习内容
     *
     * @param groupId 群号
     * @param ask     问内容
     */
    void deleteGroupLearn(Long groupId, String ask);
}
