package net.lz1998.demo.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.event.message.CQPrivateMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecurityPlugin extends CQPlugin {
    @Autowired
    UserService userService;

    @Override
    public int onPrivateMessage(CoolQ cq, CQPrivateMessageEvent event) {
        String msg = event.getMessage();
        long userId = event.getUserId();
        if (msg.startsWith("验证码")) {
            msg = msg.substring("验证码".length()).trim();
            String result = userService.register(userId, msg);

            cq.sendPrivateMsg(userId, result, false);
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }

    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
        String msg = event.getMessage();
        long groupId = event.getGroupId();
        long userId = event.getUserId();
        if (msg.startsWith("验证码")) {
            msg = msg.substring("验证码".length()).trim();
            String result = userService.register(userId, msg);

            cq.sendGroupMsg(groupId, result, false);
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }
}
