package net.lz1998.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MyGroupInfo {
    private Long groupId;
    private String groupName;
    private Long botId;
}
