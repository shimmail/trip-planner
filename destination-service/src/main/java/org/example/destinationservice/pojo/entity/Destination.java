package org.example.destinationservice.pojo.entity;

import java.time.LocalDateTime;

/**
 * destinations
 */
public class Destination {
    private LocalDateTime createdAt;
    /**
     * 行程描述
     */
    private String description;
    /**
     * ID 编号
     */
    private long id;
    /**
     * 位置
     */
    private String location;
    /**
     * 目的地名称
     */
    private String name;
    private LocalDateTime updatedAt;

    public LocalDateTime getCretedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime value) { this.createdAt = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public long getId() { return id; }
    public void setId(long value) { this.id = value; }

    public String getLocation() { return location; }
    public void setLocation(String value) { this.location = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime value) { this.updatedAt = value; }
}
