package com.example.demo.dao;

import com.example.demo.model.OauthToken;
import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {

    ArrayList<HashMap<String, Object>> findAll();

    List<User> getUser(String username);

    User getUserByName(String username);

    int createUser(User user);

    int updateUser(User user);

    int updateToken(OauthToken oauthToken);

    int deleteUser(String id);

}
