package net.lz1998.demo.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.event.message.CQPrivateMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import org.springframework.stereotype.Component;

@Component
public class PrefixPlugin extends CQPlugin {

    private static final String PREFIX = ".";

    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) { // 群聊
        String msg = event.getMessage();

        if (msg.startsWith(PREFIX)) {
            msg = msg.substring(PREFIX.length());
            event.setMessage(msg);
            return MESSAGE_IGNORE; // 继续执行下一个功能
        } else {
            return MESSAGE_BLOCK; // 拦截
        }
    }

    @Override
    public int onPrivateMessage(CoolQ cq, CQPrivateMessageEvent event) { // 私聊
        String msg = event.getMessage();

        if (msg.startsWith(PREFIX)) {
            msg = msg.substring(PREFIX.length());
            event.setMessage(msg);
            return MESSAGE_IGNORE; // 继续执行下一个功能
        } else {
            return MESSAGE_BLOCK; // 拦截
        }
    }
}
