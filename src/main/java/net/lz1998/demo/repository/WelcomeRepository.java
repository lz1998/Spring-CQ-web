package net.lz1998.demo.repository;

import net.lz1998.demo.entity.Welcome;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WelcomeRepository extends JpaRepository<Welcome, Integer> {
    /**
     * 通过群号查找欢迎信息
     *
     * @param groupId 群号
     * @return 欢迎信息
     */
    Welcome findWelcomeByGroupId(Long groupId);
}
