package org.example.userservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.pojo.entity.User;

import org.example.userservice.result.Result;
import org.example.userservice.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Result registerUser(User user) {
        if (!userMapper.findByUsername(user.getUsername()).isEmpty()) {
            return Result.error("用户名已存在");
        }

        // 校验邮箱是否已存在
        if (!userMapper.findByEmail(user.getEmail()).isEmpty()) {
            return Result.error("邮箱已存在");
        }
        // 设置注册时间
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 插入用户记录
        userMapper.insertUser(user);
        return Result.success("注册成功");
    }

    @Override
    public Result login(User user) {
        String Token = null;
        if (userMapper.findByUsername(user.getUsername()).isEmpty()) {
            return Result.error("用户名不存在");
        }

        // 检查密码是否正确
        if (!passwordEncoder.matches(user.getPassword(), user.getPassword())) {
            return Result.error("密码错误");
        }

        // 生成JWT Token


        // 返回成功结果
        return Result.success(Token);
    }
}
