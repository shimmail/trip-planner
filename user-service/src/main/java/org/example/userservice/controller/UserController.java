package org.example.userservice.controller;

import org.example.userservice.pojo.entity.User;
import org.example.userservice.result.Result;
import org.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        return userService.registerUser(user);
    }
    // 用户登录
    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        return userService.login(user);
    }
}