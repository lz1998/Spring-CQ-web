package net.lz1998.demo.service.impl;

import net.lz1998.cq.retdata.ApiData;
import net.lz1998.cq.retdata.GroupMemberInfoData;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.demo.config.Config;
import net.lz1998.demo.service.AdminService;
import net.lz1998.demo.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    BotService botService;

    @Override
    public Boolean isSuperAdmin(Long userId) {
        return Config.superAdminList.contains(userId);
    }

    @Override
    public Boolean isGroupOwner(CoolQ cq, Long groupId, Long userId) {
        // 超级管理员/群主
        ApiData<GroupMemberInfoData> senderInfo = cq.getGroupMemberInfo(groupId, userId, false);
        if (senderInfo == null) {
            return false;
        }

        return Config.superAdminList.contains(userId)
                || senderInfo.getData().getRole().equals("owner");
    }

    @Override
    public Boolean isGroupAdmin(CoolQ cq, Long groupId, Long userId) {
        // 超级管理员/群主/群管理员
        ApiData<GroupMemberInfoData> senderInfo = cq.getGroupMemberInfo(groupId, userId, false);

        if (senderInfo == null) {
            return false;
        }

        return Config.superAdminList.contains(userId)
                || senderInfo.getData().getRole().equals("owner")
                || senderInfo.getData().getRole().equals("admin");
    }

    @Override
    public Boolean isWebSuperAdmin() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return isSuperAdmin(userId);
    }

    @Override
    public Boolean isWebGroupOwner(Long groupId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CoolQ cq = botService.getBotInstance(groupId);
        if (cq == null) {
            return false;
        }
        return isGroupOwner(cq, groupId, userId);
    }

    @Override
    public Boolean isWebGroupAdmin(Long groupId) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CoolQ cq = botService.getBotInstance(groupId);
        if (cq == null) {
            return false;
        }
        return isGroupAdmin(cq, groupId, userId);
    }


}
