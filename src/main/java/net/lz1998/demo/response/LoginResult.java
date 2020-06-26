package net.lz1998.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResult {

    /**
     * 类型 登陆成功 token， 登陆失败 verificationCode
     */
    private String type;

    /**
     * 登陆结果 登陆成功 token， 登陆失败 verificationCode
     */
    private String result;
}
