package net.lz1998.demo.repository;

import net.lz1998.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 通过QQ查询用户信息
     *
     * @param userId QQ
     * @return 用户信息
     */
    User findUserByUserId(Long userId);
}
