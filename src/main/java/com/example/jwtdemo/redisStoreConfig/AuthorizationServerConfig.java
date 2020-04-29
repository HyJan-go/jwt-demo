//package com.example.jwtdemo.redisStoreConfig;
//
//import com.example.jwtdemo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//
//import javax.annotation.Resource;
//
///**
// * @program: demo
// * @description: 在认证服务器配置中指定令牌的存储策略为Redis
// * 认证服务器配置
// * @author: HyJan
// * @create: 2020-04-29 14:03
// **/
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Resource
//    private UserService userService;
//
//    /**
//     * 为了准确找到自己注入的，多加一个注解进行绑定
//     * 这里传进去的可以是指定的名称，现在的水平只看出了这个是在配置类里注入bean的方法名的值
//     */
//    @Autowired
//    @Qualifier("redisTokenStore")
//    private TokenStore tokenStore;
//
//    /**
//     * 使用密码模式需要配置
//     *
//     * @param endpoints
//     * @throws Exception
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.authenticationManager(authenticationManager)
//                .userDetailsService(userService)
//                .tokenStore(tokenStore);
//    }
//
//    /**
//     * 获取秘钥需要身份认证，使用单点登录时需要配置
//     *
//     * @param security
//     * @throws Exception
//     */
//    @Override
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        // 单点登录时必须配置
//        security.tokenKeyAccess("isAuthenticated()");
//    }
//
//    /**
//     * 对token的一些验证以及管理
//     * 客服端请求的时候 /oauth/token 就先到此处进行验证，验证通过之后，才能返回token
//     * @param clients
//     * @throws Exception
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                // 客户端的Authorization使用baseAuthor,然后用户名密码要跟这个匹配。postman测试的
//                .withClient("admin")
//                .secret(passwordEncoder.encode("admin123456"))
//                .accessTokenValiditySeconds(3600)
//                .refreshTokenValiditySeconds(864000)
//                // 授权成功后或者失败后跳转的url，单点登录的时候，这个一定要配置
//                .redirectUris("http://localhost:8000/login")
//                // 自动授权配置
//                .autoApprove(true)
//                .scopes("all")
//                // 设置授权类型 授权码，密码，刷新令牌
//                .authorizedGrantTypes("authorization_code", "password", "refresh_token");
//    }
//}
