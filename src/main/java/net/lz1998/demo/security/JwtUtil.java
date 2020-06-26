package net.lz1998.demo.security;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * Jwt工具类,从springboot-demo抄的
 */
@Component
@EnableConfigurationProperties(JwtConfig.class)
@Configuration
@Slf4j
public class JwtUtil {
    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 创建JWT
     *
     * @param rememberMe 记住我
     * @param userId     用户id
     * @return JWT
     */
    public String createJWT(Boolean rememberMe, Long userId) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(userId.toString())
                .setSubject(userId.toString())
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getKey());

        // 设置过期时间
        Long ttl = rememberMe ? jwtConfig.getRemember() : jwtConfig.getTtl();
        if (ttl > 0) {
            builder.setExpiration(new Date(System.currentTimeMillis() + ttl));
        }

        return builder.compact();
    }


    /**
     * 解析JWT
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    public Claims parseJWT(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtConfig.getKey())
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new SecurityException("Token 已过期");
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new SecurityException("不支持的 Token");
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new SecurityException("Token 无效");
        } catch (SignatureException e) {
            log.error("无效的 Token 签名");
            throw new SecurityException("无效的 Token 签名");
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new SecurityException("Token 参数不存在");
        }
    }


    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt JWT
     * @return 用户名
     */
    public Long getUserIdFromJWT(String jwt) {
        Claims claims = parseJWT(jwt);
        return Long.valueOf(claims.getId());
    }

    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
