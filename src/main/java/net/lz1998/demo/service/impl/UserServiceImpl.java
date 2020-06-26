package net.lz1998.demo.service.impl;

import lombok.Builder;
import lombok.Data;
import net.lz1998.demo.entity.User;
import net.lz1998.demo.repository.UserRepository;
import net.lz1998.demo.security.JwtUtil;
import net.lz1998.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl implements UserService {
    // 不包括 0，1，O，I
    private static final String VERIFICATION_CODE_LETTERS = "1234567890";
    private static final Integer VERIFICATION_CODE_LENGTH = 6;
    private static final Long EXPIRE_TIME = 600000L;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    Map<String, UserCache> userCacheMap = new ConcurrentHashMap<>();
    Random random = new Random();


    /**
     * 获取用户
     *
     * @param userId 用户ID，QQ
     * @return 用户
     */
    @Override
    public User getUser(Long userId) {
        return userRepository.findUserByUserId(userId);
    }

    /**
     * 登陆
     *
     * @param userId   用户ID，QQ
     * @param password 密码
     * @return 成功返回token，失败返回null
     */
    @Override
    public String login(Long userId, String password) {
        User user = userRepository.findUserByUserId(userId);


        // 账号不存在
        if (user == null) {
            return null;
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.createJWT(true, userId);// jwt
        }

        return null;
    }

    /**
     * 设置临时用户，存在Map中
     *
     * @param userId   用户ID，QQ
     * @param password 用户密码，需要加密
     * @return 验证码
     */
    @Override
    public String setTmpUser(Long userId, String password) {
        if (userCacheMap.size() > 10) {
            clearUserCacheMap();
        }
        password = passwordEncoder.encode(password);
        User user = User.builder().userId(userId).password(password).build();
        UserCache userCache = UserCache.builder().user(user).startTime(System.currentTimeMillis()).build();

        String verificationCode = getVerificationCode();
        userCacheMap.put(verificationCode, userCache);
        return verificationCode;
    }

    /**
     * 私聊收到验证码后注册
     *
     * @param verificationCode 验证码
     * @return 注册结果
     */
    @Override
    public String register(Long userId, String verificationCode) {
        if (StringUtils.isEmpty(verificationCode)) {
            return "验证码为空";
        }
        UserCache userCache = userCacheMap.get(verificationCode);
        if (userCache == null) {
            return "验证码错误";
        }

        User user = userCache.getUser();
        // 校验是否本人注册
        if (userId.equals(user.getUserId())) {
            userRepository.save(user);
            userCacheMap.remove(verificationCode);
            return "设置成功";
        }
        return "验证码错误";
    }

    /**
     * 获取验证码
     *
     * @return 验证码
     */
    private synchronized String getVerificationCode() {
        StringBuilder verificationCode = new StringBuilder();
        for (int i = 0; i < VERIFICATION_CODE_LENGTH; i++) {
            verificationCode.append(VERIFICATION_CODE_LETTERS.charAt(random.nextInt(VERIFICATION_CODE_LETTERS.length())));
        }

        // 如果重复，重新生成
        if (userCacheMap.containsKey(verificationCode.toString())) {
            verificationCode = new StringBuilder(getVerificationCode());
        }
        return verificationCode.toString();
    }

    /**
     * 清理已过期的验证码
     */
    private void clearUserCacheMap() {
        Object[] verificationCodeList = userCacheMap.keySet().toArray();
        for (Object verificationCodeObj : verificationCodeList) {
            String verificationCode = (String) verificationCodeObj;
            if (userCacheMap.get(verificationCode).startTime + EXPIRE_TIME < System.currentTimeMillis()) {
                userCacheMap.remove(verificationCode);
            }
        }
    }

    @Builder
    @Data
    static public class UserCache {
        private User user;
        private Long startTime;
    }
}
