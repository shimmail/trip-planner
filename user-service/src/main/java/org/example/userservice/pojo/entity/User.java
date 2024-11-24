package org.example.userservice.pojo.entity;
import java.time.LocalDateTime;

/**
 * users
 */
public class User {
    private LocalDateTime createdAt;
    private String email;
    private Long id;
    private String password;
    private LocalDateTime updatedAt;
    private String username;

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime value) { this.createdAt = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public Long getId() { return id; }
    public void setId(long value) { this.id = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { this.password = value; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime value) { this.updatedAt = value; }

    public String getUsername() { return username; }
    public void setUsername(String value) { this.username = value; }
}
