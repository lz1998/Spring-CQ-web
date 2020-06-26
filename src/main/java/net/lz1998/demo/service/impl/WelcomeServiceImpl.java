package net.lz1998.demo.service.impl;

import net.lz1998.demo.entity.Welcome;
import net.lz1998.demo.repository.WelcomeRepository;
import net.lz1998.demo.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WelcomeServiceImpl implements WelcomeService {
    @Autowired
    private WelcomeRepository welcomeRepository;

    /**
     * 设置欢迎内容
     *
     * @param groupId    群号
     * @param welcomeMsg 欢迎内容
     * @param adminId    管理员QQ
     */
    @Override
    public void setWelcomeMsg(Long groupId, String welcomeMsg, Long adminId) {
        Welcome welcome = Welcome.builder()
                .groupId(groupId)
                .welcomeMsg(welcomeMsg)
                .adminId(adminId)
                .build();
        // 必须处理默认，否则缓存会出问题
        welcomeRepository.save(welcome);
    }

    /**
     * 获取欢迎内容
     *
     * @param groupId 群号
     * @return 欢迎内容
     */
    @Override
    public String getWelcomeMsg(Long groupId) {
        Welcome welcome = welcomeRepository.findWelcomeByGroupId(groupId);
        if (welcome == null) {
            // 如果群内没有设置，那么是默认消息
            return "";
        }
        return welcome.getWelcomeMsg();
    }

}
