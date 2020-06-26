package net.lz1998.demo.controller;

import net.lz1998.demo.response.LoginResult;
import net.lz1998.demo.response.Response;
import net.lz1998.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于登陆注册找回密码
 */
@CrossOrigin
@RequestMapping("/api/security")
@RestController
public class SecurityController {

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public Object login(Long userId, String password) {
        LoginResult loginResult;
        String token = userService.login(userId, password);

        if (token != null) {
            // 登陆成功，得到token
            loginResult = new LoginResult("token", token);
            return Response.getSuccessResponse(10000, "登陆成功", loginResult);
        } else {
            // 登陆失败，注册/修改密码，获取验证码
            String verificationCode = userService.setTmpUser(userId, password);
            loginResult = new LoginResult("verification", verificationCode);
            return Response.getResponse(loginResult);
        }
    }


}
