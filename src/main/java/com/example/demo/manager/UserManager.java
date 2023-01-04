package com.example.demo.manager;

import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserManager {

    @Autowired
    UserMapper userMapper;

    public ArrayList<HashMap<String, Object>> findAll() {
        return userMapper.findAll();
    }
    public List<User> getUser(String userId) {
        return userMapper.getUser(userId);
    }

    public int createUser(User user) {
        return userMapper.createUser(user);
    }

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public int deleteUser(String id) {
        return userMapper.deleteUser(id);
    }


}
