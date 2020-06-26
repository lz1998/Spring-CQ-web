package net.lz1998.demo.service;


public interface AuthService {
    /**
     * 是否已授权
     *
     * @param groupId 群号
     * @return 是否已授权
     */
    Boolean isAuth(Long groupId);

    /**
     * 设置授权
     *
     * @param groupId 群号
     * @param isAuth  添加/删除授权
     * @param adminId 操作管理员QQ
     * @return 操作后是否授权
     */
    Boolean setAuth(Long groupId, Boolean isAuth, Long adminId);
}
