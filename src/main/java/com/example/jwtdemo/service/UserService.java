package com.example.jwtdemo.service;

import com.example.jwtdemo.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: demo
 * @description: 这个 service 必须实现 UserDetailsService，只通过用户名来判断用户是否存在，然后进行其他逻辑
 * @author: HyJan
 * @create: 2020-04-29 14:17
 **/
@Service
public class UserService implements UserDetailsService {

    /**
     * 这个User实体类也不能跟平时的一样，这个是要注意的，具体要进去实体类里面查看才知道
     */
    private List<User> users;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 这个注解的方法，会在类初始化之后，接着就执行
     * 用来初始化数据使用
     */
    @PostConstruct
    public void initData(){
        String password = passwordEncoder.encode("123456");
        users = new ArrayList<>();
        users.add(new User("andy", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        users.add(new User("liming", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        users.add(new User("aKing", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        users.add(new User("John", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
    }

    /**
     * 通过用户名指定用户是否存在
     * @param userName
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        List<User> findUsers = users.stream().filter(user -> user.getUsername().equals(userName))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(findUsers)){
            return findUsers.get(0);
        }else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }
}
