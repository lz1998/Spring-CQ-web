package net.lz1998.demo.controller;

import net.lz1998.cq.event.meta.CQHeartBeatMetaEvent;
import net.lz1998.demo.plugin.StatusPlugin;
import net.lz1998.demo.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * 查询机器人基本信息
 */
@CrossOrigin
@RequestMapping("/api/bot")
@RestController
public class BotController {

    @Autowired
    private StatusPlugin statusPlugin;

    /**
     * 查询机器人状态和数量（心跳）
     *
     * @return 所有机器人状态信息
     */
    @RequestMapping("/getStatus")
    public Object getStatus() {
        Collection<CQHeartBeatMetaEvent> cqHeartBeatMetaEvents = statusPlugin.getHeartBeatMap().values();
        cqHeartBeatMetaEvents.forEach(cqHeartBeatMetaEvent -> cqHeartBeatMetaEvent.getStatus().setPluginsGood(null));
        return Response.getResponse(cqHeartBeatMetaEvents);
    }
}
