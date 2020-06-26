package net.lz1998.demo.service;

import net.lz1998.cq.robot.CoolQ;

public interface AdminService {
    /**
     * 是否超级管理员
     *
     * @return
     */
    Boolean isSuperAdmin(Long userId);

    /**
     * 是否群主
     *
     * @return
     */
    Boolean isGroupOwner(CoolQ cq, Long groupId, Long userId);

    /**
     * 是否管理员
     *
     * @return
     */
    Boolean isGroupAdmin(CoolQ cq, Long groupId, Long userId);

    /**
     * 是否超级管理员
     * 使用spring security获取userId
     *
     * @return
     */
    Boolean isWebSuperAdmin();

    /**
     * 是否群主
     * 从spring security获取userId
     * 从BotService获取cq对象
     *
     * @return
     */
    Boolean isWebGroupOwner(Long groupId);

    /**
     * 是否管理员
     * 使用spring security获取userId
     * 从BotService获取cq对象
     * 如果群不存在，会返回false
     *
     * @return
     */
    Boolean isWebGroupAdmin(Long groupId);

}
