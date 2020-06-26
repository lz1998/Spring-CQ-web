package net.lz1998.demo.controller;

import net.lz1998.demo.entity.Learn;
import net.lz1998.demo.entity.MyGroupInfo;
import net.lz1998.demo.response.GroupLearnPageResult;
import net.lz1998.demo.response.Response;
import net.lz1998.demo.service.AdminService;
import net.lz1998.demo.service.BotService;
import net.lz1998.demo.service.LearnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于增删改查 每个群自定义的学习内容
 */
@CrossOrigin
@RequestMapping("/api/learn")
@RestController
public class LearnController {

    @Autowired
    LearnService learnService;
    @Autowired
    BotService botService;
    @Autowired
    AdminService adminService;

    @RequestMapping("/getGroupLearnPage")
    public Object getGroupLearnPage(Long groupId, @PageableDefault Pageable pageable) {
        MyGroupInfo myGroupInfo = botService.getMyGroupInfo(groupId);
        if (myGroupInfo == null) {
            return Response.getFailResponse(-30000, "群不存在", false);
        }
        Page<Learn> page = learnService.getGroupLearnPage(groupId, pageable);
        boolean canUpdate = adminService.isWebGroupAdmin(groupId);
        GroupLearnPageResult result = new GroupLearnPageResult(page, myGroupInfo.getGroupName(), canUpdate);
        return Response.getResponse(result);
    }

    @RequestMapping("/setGroupLearn")
    public Object setGroupLearn(Long groupId, String ask, String answer) {
        // 检测权限
        if (!adminService.isWebGroupAdmin(groupId)) {
            return Response.getFailResponse(-20000, "权限不足", false);
        }
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        learnService.setAnswer(groupId, ask, answer, userId);
        return Response.getResponse(true);
    }

    @RequestMapping("/deleteGroupLearn")
    public Object deleteGroupLearn(Long groupId, String ask) {
        if (!adminService.isWebGroupAdmin(groupId)) {
            return Response.getFailResponse(-20000, "权限不足", false);
        }
        learnService.deleteGroupLearn(groupId, ask);
        return Response.getResponse(true);
    }
}
