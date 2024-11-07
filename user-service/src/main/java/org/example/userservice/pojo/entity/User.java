package org.example.userservice.pojo.entity;
import java.time.OffsetDateTime;

/**
 * users
 */
public class User {
    private OffsetDateTime createdAt;
    private String email;
    private long id;
    private String password;
    private OffsetDateTime updatedAt;
    private String username;

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime value) { this.createdAt = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public long getId() { return id; }
    public void setId(long value) { this.id = value; }

    public String getPassword() { return password; }
    public void setPassword(String value) { this.password = value; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime value) { this.updatedAt = value; }

    public String getUsername() { return username; }
    public void setUsername(String value) { this.username = value; }
}
