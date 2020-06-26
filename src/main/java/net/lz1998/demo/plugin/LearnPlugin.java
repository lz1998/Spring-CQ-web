package net.lz1998.demo.plugin;

import net.lz1998.cq.event.message.CQGroupMessageEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.demo.service.AdminService;
import net.lz1998.demo.service.LearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class LearnPlugin extends CQPlugin {
    @Autowired
    private LearnService learnService;
    @Autowired
    private AdminService adminService;


    @Override
    public int onGroupMessage(CoolQ cq, CQGroupMessageEvent event) {
        String msg = event.getMessage();
        Long userId = event.getUserId();
        long groupId = event.getGroupId();

        msg = msg.replace("问：", "问:");
        msg = msg.replace("答：", "答:");
        if (msg.equals("清空学习")) {
            if (!adminService.isGroupAdmin(cq, groupId, userId)) { // 无权限
                return MESSAGE_IGNORE;
            }
            learnService.clear(groupId);
            cq.sendGroupMsg(groupId, "已清空", false);
            return MESSAGE_BLOCK;
        }
        if (msg.startsWith("问:")) {
            if (!adminService.isGroupAdmin(cq, groupId, userId)) { // 无权限
                return MESSAGE_IGNORE;
            }
            msg = msg.substring("问:".length());
            String[] tmp = msg.split("答:");
            if (tmp.length < 2) {
                cq.sendGroupMsg(groupId, "格式错误", false);
                return MESSAGE_BLOCK;
            }
            String ask = tmp[0].trim(); // 去除左右空格，MySQL不敏感
            String answer = tmp[1];

            if (ask.length() > 100) {
                cq.sendGroupMsg(groupId, "错误：问 最大长度是100", false);
                return MESSAGE_BLOCK;
            }
            if (answer.length() > 2000) {
                cq.sendGroupMsg(groupId, "错误：答 最大长度是2000", false);
                return MESSAGE_BLOCK;
            }


            learnService.setAnswer(groupId, ask, answer, userId);
            cq.sendGroupMsg(groupId, "已学会", false);
            return MESSAGE_BLOCK;
        }


        String answer = learnService.getAnswer(groupId, msg);
        if (!StringUtils.isEmpty(answer)) {
            answer = answer.replace("{{userId}}", userId.toString());
            cq.sendGroupMsg(groupId, answer, false);
            return MESSAGE_BLOCK;
        }
        return MESSAGE_IGNORE;
    }

}
