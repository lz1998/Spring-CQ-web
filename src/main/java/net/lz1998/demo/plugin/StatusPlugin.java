package net.lz1998.demo.plugin;

import lombok.Getter;
import net.lz1998.cq.event.meta.CQHeartBeatMetaEvent;
import net.lz1998.cq.robot.CQPlugin;
import net.lz1998.cq.robot.CoolQ;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 状态监控插件
 * 用于检测心跳包
 * 把所有机器人在线状态显示在网页上（BotController用到）
 */
@Component
public class StatusPlugin extends CQPlugin {

    @Getter
    private Map<Long, CQHeartBeatMetaEvent> heartBeatMap = new ConcurrentHashMap<>();


    @Override
    public int onHeartBeatMeta(CoolQ cq, CQHeartBeatMetaEvent event) {
        Long selfId = cq.getSelfId();
        heartBeatMap.put(selfId, event);
        return MESSAGE_BLOCK;
    }

}
