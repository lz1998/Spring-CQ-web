package net.lz1998.demo.service;

import net.lz1998.cq.robot.CoolQ;
import net.lz1998.demo.entity.MyGroupInfo;

/**
 * 通用服务
 * 通过群号查询机器人号、群名称
 * 通过机器人号查询群列表
 */
public interface BotService {
    /**
     * 获取群信息
     *
     * @param groupId 群号
     * @return 群信息
     */
    MyGroupInfo getMyGroupInfo(Long groupId);

    /**
     * 获取一个群在哪个机器人上（可能存在多个机器人）
     *
     * @param groupId 群号
     * @return 机器人号
     */
    Long getBotId(Long groupId);

    /**
     * 获取群号所在机器人对象（可能存在多个机器人）
     *
     * @param groupId 群号
     * @return 机器人对象
     */
    CoolQ getBotInstance(Long groupId);

    /**
     * 刷新群数据
     */
    void refreshGroupData();
}
