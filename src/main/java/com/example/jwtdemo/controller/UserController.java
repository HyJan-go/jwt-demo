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
     *
     *  从头部获取jwt 然后进行进行解析获取真正的信息
     *
     * 适合使用jwt的场景：
     *
     * 有效期短
     * 只希望被使用一次
     *
     * 比如，用户注册后发一封邮件让其激活账户，通常邮件中需要有一个链接，这个链接需要具备以下的特性：能够标识用户，
     * 该链接具有时效性（通常只允许几小时之内激活），不能被篡改以激活其他可能的账户，一次性的。这种场景就适合使用jwt。
     *
     * 而由于jwt具有一次性的特性。单点登录和会话管理非常不适合用jwt，如果在服务端部署额外的逻辑存储jwt的状态，
     * 那还不如使用session。基于session有很多成熟的框架可以开箱即用，但是用jwt还要自己实现逻辑。
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
