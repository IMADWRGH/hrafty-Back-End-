package com.hrafty.web_app.services;

import com.hrafty.web_app.Repository.UserRepository;
import com.hrafty.web_app.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


}
