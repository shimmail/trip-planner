package org.example.userservice.service;


import org.example.api.dto.UserDTO;
import org.example.userservice.result.Result;

public interface UserService {
        Result registerUser(UserDTO user);

        Result login(UserDTO userDTO);
}
