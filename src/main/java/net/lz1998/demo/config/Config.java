package net.lz1998.demo.config;

import java.util.ArrayList;
import java.util.List;

public class Config {
    /**
     * 超级管理员列表，在这里配置最高权限的管理员
     * TODO 可以改成yml读取
     */
    public static List<Long> superAdminList = new ArrayList<>();

    static {
        superAdminList.add(875543533L);
    }
}
