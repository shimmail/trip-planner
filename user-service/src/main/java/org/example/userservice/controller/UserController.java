package org.example.userservice.controller;

import org.example.api.dto.UserDTO;
import org.example.userservice.pojo.entity.User;
import org.example.userservice.result.Result;
import org.example.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }
    // 用户登录
    @PostMapping("/login")
    public Result login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }
}