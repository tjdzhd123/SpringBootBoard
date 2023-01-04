package com.example.demo.service;

import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("oAuth2UserService")
@Slf4j
public class OAuth2UserService implements UserDetailsService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=1번===========loadUserByUsername======== "+username);
        User userInfo = userMapper.getUserByName(username);
        System.out.println("=2번=========loadUserByUsername======== "+userInfo.toString());
        return userInfo;
    }

}
