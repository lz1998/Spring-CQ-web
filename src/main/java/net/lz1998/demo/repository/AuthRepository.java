package net.lz1998.demo.repository;

import net.lz1998.demo.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthRepository extends JpaRepository<Auth, Integer> {
    /**
     * 通过群号查询授权
     *
     * @param groupId 群号
     * @return 授权
     */
    Auth findAuthByGroupId(Long groupId);

    /**
     * 查询已授权的所有群
     *
     * @return 授权列表
     */
    List<Auth> findAuthsByIsAuthTrue();
}
