package net.lz1998.demo.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.event.notice.CQGroupIncreaseNoticeEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.demo.service.AdminService;
import net.lz1998.demo.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class WelcomePlugin extends CQPlugin {

    @Autowired
    private WelcomeService welcomeService;
    @Autowired
    private AdminService adminService;

    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
        String msg = event.getMessage();
        Long userId = event.getUserId();
        long groupId = event.getGroupId();
        if (msg.startsWith("设置欢迎") && adminService.isGroupAdmin(cq, groupId, userId)) {
            msg = msg.substring("设置欢迎".length()).trim();
            if (msg.length() > 2000) {
                cq.sendGroupMsg(groupId, "错误：欢迎 最大长度2000", false);
                return MESSAGE_BLOCK;
            }

            welcomeService.setWelcomeMsg(groupId, msg, userId);
            cq.sendGroupMsg(groupId, "设置成功", false);
            return MESSAGE_BLOCK;
        }
        if (msg.startsWith("查看欢迎") && adminService.isGroupAdmin(cq, groupId, userId)) {
            String welcomeMsg = welcomeService.getWelcomeMsg(groupId);

            if (!StringUtils.isEmpty(welcomeMsg)) {
                // 假设进群者、审批者都是自己
                welcomeMsg = welcomeMsg.replace("{{userId}}", userId.toString());
                welcomeMsg = welcomeMsg.replace("{{operatorId}}", userId.toString());
                cq.sendGroupMsg(groupId, welcomeMsg, false);
                return MESSAGE_BLOCK;
            }

            welcomeMsg = "无";
            cq.sendGroupMsg(groupId, welcomeMsg, false);
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }

    @Override
    public int onGroupIncreaseNotice(CoolQ cq, CQGroupIncreaseNoticeEvent event) {
        long groupId = event.getGroupId();
        long userId = event.getUserId();
        long operatorId = event.getOperatorId();

        String welcomeMsg = welcomeService.getWelcomeMsg(groupId);
        if (!StringUtils.isEmpty(welcomeMsg)) {
            welcomeMsg = welcomeMsg.replace("{{userId}}", Long.toString(userId));
            welcomeMsg = welcomeMsg.replace("{{operatorId}}", Long.toString(operatorId));
            cq.sendGroupMsg(groupId, welcomeMsg, false);
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }


}
