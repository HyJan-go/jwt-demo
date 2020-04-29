package com.example.jwtdemo.jwtStoreConfig;

import com.example.jwtdemo.component.JwtTokenEnhancer;
import com.example.jwtdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: demo
 * @description: 使用jwt的方式进行存储的配置类
 * @author: HyJan
 * @create: 2020-04-29 16:53
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    /**
     * 为了准确找到自己注入的，多加一个注解进行绑定
     * 这里传进去的可以是指定的名称，现在的水平只看出了这个是在配置类里注入bean的方法名的值
     */
    @Autowired
    @Qualifier("jwtTokenStore")
    private TokenStore tokenStore;

    /**
     * jwt 存储一定要注入转换这个 bean
     */
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private JwtTokenEnhancer jwtTokenEnhancer;

    /**
     * 一般单点登录时配置
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //使用单点登录一定要配置的,获取秘钥需要身份认证
        security.tokenKeyAccess("isAuthenticated()");
    }

    /**
     * 客户端请求进来的时候先经过此验证
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("admin")
                .secret(passwordEncoder.encode("admin123456"))
                .accessTokenValiditySeconds(3600)
                .refreshTokenValiditySeconds(864000)
                // 如果是单点登录，这里一定要设置
                .redirectUris("http://www.baidu.com")
                // 自动授权配置
                .autoApprove(true)
                .scopes("all")
                .authorizedGrantTypes("authorization_code","password","refresh_token");
    }

    /**
     * 使用密码模式需要配置
     *
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> delegates = new ArrayList<>();
        // 配置jwt的内容增强器
        delegates.add(jwtTokenEnhancer);
        delegates.add(jwtAccessTokenConverter);
        // 设置好后放进它的链圈中
        tokenEnhancerChain.setTokenEnhancers(delegates);
        endpoints.authenticationManager(authenticationManager)
                // 配置令牌存储策略
                .tokenStore(tokenStore)
                .userDetailsService(userService)
                .accessTokenConverter(jwtAccessTokenConverter)
                .tokenEnhancer(tokenEnhancerChain);
    }
}
