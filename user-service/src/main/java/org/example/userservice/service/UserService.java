package org.example.userservice.service;

import org.example.userservice.pojo.dto.UserDTO;
import org.example.userservice.pojo.entity.User;
import org.example.userservice.result.Result;

public interface UserService {
        Result registerUser(User user);

        Result login(UserDTO userDTO);
}
