//package com.example.jwtdemo.redisStoreConfig;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
///**
// * @program: demo
// * @description: redis 存储 token 的配置
// * 亲测redis的存储是成功了的
// * @author: HyJan
// * @create: 2020-04-29 13:59
// **/
//@Configuration
//public class RedisTokenStoreConfig {
//
//    @Autowired
//    private RedisConnectionFactory redisConnectionFactory;
//
//    @Bean
//    public TokenStore redisTokenStore(){
//        return new RedisTokenStore(redisConnectionFactory);
//    }
//}
