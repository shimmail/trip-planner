package org.example.destinationservice.pojo.dto;

import java.time.OffsetDateTime;

/**
 * destinations
 */
public class DestinationDTO {
    private OffsetDateTime createdAt;
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
    private OffsetDateTime updatedAt;

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime value) { this.createdAt = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public long getId() { return id; }
    public void setId(long value) { this.id = value; }

    public String getLocation() { return location; }
    public void setLocation(String value) { this.location = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(OffsetDateTime value) { this.updatedAt = value; }
}
