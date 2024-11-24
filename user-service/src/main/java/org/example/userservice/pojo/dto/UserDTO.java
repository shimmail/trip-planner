package org.example.userservice.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private LocalDateTime createdAt;
    private String email;
    private Long id;
    private String password;
    private LocalDateTime updatedAt;
    private String username;

}
