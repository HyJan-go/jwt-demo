package com.example.jwtdemo.jwtStoreConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @program: demo
 * @description: 使用jwt存储token的配置
 * 使用了jwt就不要redis，两者只能选一个
 * @author: HyJan
 * @create: 2020-04-29 16:42
 **/
@Configuration
public class JwtTokenStoreConfig {

    @Value("${spring.security.oauth2.jwt.assign-key}")
    private String assignKey;

    /**
     * 通过转化器来建立一个jwt的存储方式
     * @return
     */
    @Bean
    public TokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    /**
     * 使用JWT的方式，就一定要配置转化器
     * @return
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        // 配置jwt使用的秘钥
        jwtAccessTokenConverter.setSigningKey(assignKey);
        return jwtAccessTokenConverter;
    }
}
