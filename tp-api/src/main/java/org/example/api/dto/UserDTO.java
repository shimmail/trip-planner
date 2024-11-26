package org.example.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String email;
    private Long id;
    private String password;
    private String username;


}
