package net.lz1998.demo.controller;

import net.lz1998.demo.entity.MyGroupInfo;
import net.lz1998.demo.response.Response;
import net.lz1998.demo.response.WelcomeResult;
import net.lz1998.demo.service.AdminService;
import net.lz1998.demo.service.BotService;
import net.lz1998.demo.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于设置入群欢迎内容
 */
@CrossOrigin
@RequestMapping("/api/welcome")
@RestController
public class WelcomeController {

    @Autowired
    AdminService adminService;

    @Autowired
    BotService botService;

    @Autowired
    WelcomeService welcomeService;

    /**
     * 查询入群欢迎内容
     *
     * @param groupId 群号
     * @return WelcomeResult
     */
    @RequestMapping("/getWelcome")
    public Object getWelcome(Long groupId) {
        MyGroupInfo myGroupInfo = botService.getMyGroupInfo(groupId);
        if (myGroupInfo == null) {
            return Response.getFailResponse(-30000, "群不存在", false);
        }
        String welcomeMsg = welcomeService.getWelcomeMsg(groupId);
        boolean canUpdate = adminService.isWebGroupAdmin(groupId);
        WelcomeResult result = new WelcomeResult(welcomeMsg, myGroupInfo.getGroupName(), canUpdate);
        return Response.getResponse(result);
    }

    /**
     * 设置入群欢迎内容
     *
     * @param groupId    群号
     * @param welcomeMsg 欢迎内容
     * @return true
     */
    @RequestMapping("/setWelcome")
    public Object setWelcome(Long groupId, String welcomeMsg) {
        // 检测权限
        if (!adminService.isWebGroupAdmin(groupId)) {
            return Response.getFailResponse(-20000, "权限不足", false);
        }
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        welcomeService.setWelcomeMsg(groupId, welcomeMsg, userId);
        return Response.getResponse(true);
    }
}
