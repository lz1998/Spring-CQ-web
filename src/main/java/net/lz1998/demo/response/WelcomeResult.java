package net.lz1998.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class WelcomeResult {
    private String welcomeMsg;
    private String groupName;
    private boolean canUpdate;
}
