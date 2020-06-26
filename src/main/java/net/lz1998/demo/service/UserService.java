package net.lz1998.demo.service;


import net.lz1998.demo.entity.User;

public interface UserService {
    User getUser(Long userId);

    /**
     * 登陆，成功返回token，失败返回null
     *
     * @param userId   用户ID，QQ
     * @param password 密码
     * @return 成功返回token，失败返回null
     */
    String login(Long userId, String password);

    /**
     * 设置临时用户，存在Map中，返回验证码
     *
     * @param userId   用户ID，QQ
     * @param password 用户密码，需要加密
     * @return 验证码
     */
    String setTmpUser(Long userId, String password);

    /**
     * 私聊收到验证码后注册,从Map中读取，存入MySQL
     *
     * @param verificationCode 验证码
     * @return 注册结果
     */
    String register(Long userId, String verificationCode);
}
