package net.lz1998.demo.repository;

import net.lz1998.demo.entity.Learn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface LearnRepository extends JpaRepository<Learn, Integer> {
    /**
     * 通过 群号 和 问 寻找第一个学习内容
     *
     * @param groupId 群号
     * @param ask     问
     * @return 学习内容
     */
    Learn findFirstByGroupIdAndAsk(Long groupId, String ask);

    /**
     * 清空
     *
     * @param groupId 群号
     */
    @Transactional
    void deleteLearnsByGroupId(Long groupId);

    /**
     * 查询一页
     *
     * @param groupId  群号
     * @param pageable 页信息
     * @return 一页学习结果
     */
    Page<Learn> findLearnsByGroupId(Long groupId, Pageable pageable);

    /**
     * 通过 群号 和 问 删除一项
     *
     * @param groupId 群号
     * @param ask     问
     */
    @Transactional
    void deleteLearnByGroupIdAndAsk(Long groupId, String ask);
}
