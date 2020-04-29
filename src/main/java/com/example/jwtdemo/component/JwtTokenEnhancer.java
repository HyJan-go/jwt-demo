package com.example.jwtdemo.component;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: demo
 * @description: Jwt内容增强器   实现TokenEnhancer接口
 * @author: HyJan
 * @create: 2020-04-29 17:03
 **/
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    /**
     * 增强方法，要重写
     * @param accessToken
     * @param oAuth2Authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> info = new HashMap<>();
        // 看情况实际添加一些信息，可以先从配置文件中取，这样好维护一些
        info.put("something","something plus");
        //向上转型并设置添加信息
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
        return accessToken;
    }
}
