package com.example.jwtdemo.controller;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @program: demo
 * @description: 测试控制类
 * @author: HyJan
 * @create: 2020-04-29 16:05
 **/
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Value("${spring.security.oauth2.jwt.assign-key}")
    private String assignKey;

    /**
     * 获取当前用户的信息
     * 从头部获取jwt 然后进行进行解析获取真正的信息
     *
     * @param authorization
     * @param authentication
     * @param request
     * @return
     */
    @GetMapping("/userInfo")
    public Object GetCurrentUser(@RequestHeader("Authorization") String authorization, Authentication authentication, HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        log.info("获取到的签证为 {}", header);
        log.info("注解拿到的 {}", authorization);
        // 使用工具类的工具进行字符串的截取
        String token = StrUtil.subAfter(authorization, "bearer ", false);
        return Jwts.parser()
                // 设置签证秘钥
                .setSigningKey(assignKey.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }

}
