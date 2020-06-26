package net.lz1998.demo.service.impl;

import net.lz1998.cq.CQGlobal;
import net.lz1998.cq.retdata.GroupData;
import net.lz1998.cq.robot.CoolQ;
import net.lz1998.demo.entity.Auth;
import net.lz1998.demo.entity.MyGroupInfo;
import net.lz1998.demo.repository.AuthRepository;
import net.lz1998.demo.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class BotServiceImpl implements BotService {
    @Autowired
    AuthRepository authRepository;

    // key 群号，value 机器人号,群号,群名称
    private Map<Long, MyGroupInfo> groupInfoMap = new ConcurrentHashMap<>();

    // key 机器人号，value 机器人号,群号,群名称 List
    private Map<Long, List<MyGroupInfo>> botGroupMap = new ConcurrentHashMap<>();

    private Long lastRefreshTime = 0L;
    private static final Long REFRESH_INTERVAL = 600000L;

    @Override
    public MyGroupInfo getMyGroupInfo(Long groupId) {
        MyGroupInfo myGroupInfo = groupInfoMap.get(groupId);
        if (myGroupInfo == null) {
            // 刷新数据 双校验法 防止高并发情况下多次刷新
            if (System.currentTimeMillis() - lastRefreshTime > REFRESH_INTERVAL) {
                synchronized (this) {
                    if (System.currentTimeMillis() - lastRefreshTime > REFRESH_INTERVAL) {
                        lastRefreshTime = System.currentTimeMillis();
                        refreshGroupData();
                        myGroupInfo = groupInfoMap.get(groupId);
                    }
                }
            }

        }

        return myGroupInfo;
    }

    @Override
    public Long getBotId(Long groupId) {
        MyGroupInfo myGroupInfo = getMyGroupInfo(groupId);
        if (myGroupInfo == null) {
            return null;
        }
        return myGroupInfo.getBotId();
    }

    @Override
    public CoolQ getBotInstance(Long groupId) {
        Long botId = getBotId(groupId);
        if (botId == null) {
            return null;
        }
        return CQGlobal.robots.get(botId);
    }

    @Override
    public void refreshGroupData() {

        List<Long> authGroups = authRepository.findAuthsByIsAuthTrue().stream().map(Auth::getGroupId).collect(Collectors.toList());
        // 对机器人循环
        for (Map.Entry<Long, CoolQ> entry : CQGlobal.robots.entrySet()) {
            Long selfId = entry.getKey();
            CoolQ cq = entry.getValue();

            // 获取一个机器人的所有群
            List<GroupData> groupDataList = cq.getGroupList().getData();

            // 过滤未授权的群
            groupDataList = groupDataList.stream().filter(groupData -> authGroups.contains(groupData.getGroupId())).collect(Collectors.toList());

            // 清空List
            botGroupMap.put(selfId, new ArrayList<>());

            // 对群循环，加入两个Map
            for (GroupData groupData : groupDataList) {
                MyGroupInfo myGroupInfo = new MyGroupInfo(groupData.getGroupId(), groupData.getGroupName(), selfId);
                groupInfoMap.put(groupData.getGroupId(), myGroupInfo);
                botGroupMap.get(selfId).add(myGroupInfo);
            }
        }
    }
}
