package net.lz1998.demo.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.event.request.CQGroupRequestEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.demo.service.AdminService;
import net.lz1998.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AuthPlugin extends CQPlugin {
    @Autowired
    private AuthService authService;
    @Autowired
    private AdminService adminService;

    private static final Long INTERVAL = 60 * 60 * 1000L;// 多长时间提示一次


    /**
     * 用于保存上次提示时间
     * 如果未授权，60分钟最多提示一次
     */
    private static Map<Long, Long> noticeTimeMap = new ConcurrentHashMap<>();


    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
        long groupId = event.getGroupId();
        Long senderId = event.getSender().getUserId();

        if (adminService.isSuperAdmin(senderId)) {
            String msg = event.getMessage();
            if ("授权".equals(msg)) {
                authService.setAuth(groupId, true, senderId);
                String retMsg = "已授权" + groupId;
                cq.sendGroupMsg(groupId, retMsg, false);
                return MESSAGE_BLOCK;
            }
            if (msg.startsWith("授权+")) {
                msg = msg.substring("授权+".length()).trim();
                Long authGroupId = Long.valueOf(msg);
                authService.setAuth(authGroupId, true, senderId);
                cq.sendGroupMsg(groupId, "已授权" + authGroupId, false);
                return MESSAGE_BLOCK;
            }
            if (msg.startsWith("授权-")) {
                msg = msg.substring("授权-".length()).trim();
                Long authGroupId = Long.valueOf(msg);
                authService.setAuth(authGroupId, false, senderId);
                cq.sendGroupMsg(groupId, "取消授权" + authGroupId, false);
                return MESSAGE_BLOCK;
            }
            if (msg.startsWith("退群")) {
                msg = msg.substring("退群".length()).trim();
                long leaveGroupId = Long.parseLong(msg);
                cq.setGroupLeave(leaveGroupId, false);
                cq.sendGroupMsg(groupId, "已退群" + leaveGroupId, false);
                return MESSAGE_BLOCK;
            }
        }

        if (authService.isAuth(groupId)) {
            // 已授权，通过
            return MESSAGE_IGNORE;
        } else {
            // 未授权，间隔多久提示一次
            noticeTimeMap.putIfAbsent(groupId, 0L);
            long lastNoticeTime = noticeTimeMap.get(groupId);
            long now = System.currentTimeMillis();
            if (now - lastNoticeTime > INTERVAL) {
                cq.sendGroupMsg(groupId, "未授权，请先找管理员申请", false);
                noticeTimeMap.put(groupId, now);
            }
            return MESSAGE_BLOCK;
        }
    }

    @Override
    public int onGroupRequest(CoolQ cq, CQGroupRequestEvent event) {
        // 超级管理员邀请进群或申请进群直接通过
        Long userId = event.getUserId();
        if (adminService.isSuperAdmin(userId)) {
            cq.setGroupAddRequest(event.getFlag(), event.getSubType(), true, "");
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }


}
