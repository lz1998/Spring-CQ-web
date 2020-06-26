package net.lz1998.demo.service;

public interface WelcomeService {
    /**
     * 保存入群欢迎内容
     *
     * @param groupId    群号
     * @param welcomeMsg 欢迎内容
     * @param adminId    管理员QQ
     */
    void setWelcomeMsg(Long groupId, String welcomeMsg, Long adminId);


    /**
     * 获取欢迎内容
     *
     * @param groupId 群号
     * @return 欢迎内容
     */
    String getWelcomeMsg(Long groupId);
}
