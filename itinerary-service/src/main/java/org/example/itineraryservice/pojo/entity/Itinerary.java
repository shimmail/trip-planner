package org.example.itineraryservice.pojo.entity;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * itineraries
 */
public class Itinerary {

    private static long idCounter = 1;
    /**
     * ID 编号
     */
    private long id;
    private LocalDateTime createdAt;
    /**
     * 行程描述
     */
    private String description;
    /**
     * 外键，关联目的地表
     */
    private long destinationId;
    /**
     * 结束日期
     */
    private LocalDateTime endDate;

    /**
     * 开始日期
     */
    private LocalDateTime startDate;
    /**
     * 行程状态：0=待办，1=已完成
     */
    private Long status;
    private LocalDateTime updatedAt;
    /**
     * 外键，关联用户表
     */
    private long userId;

    public Itinerary(LocalDateTime createdAt, String description, long destinationId, LocalDateTime endDate, LocalDateTime startDate, Long status, LocalDateTime updatedAt, long userId) {
        this.createdAt = createdAt;
        this.description = description;
        this.destinationId = destinationId;
        this.endDate = endDate;
        this.startDate = startDate;
        this.status = status;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.id = idCounter++;
    }

    public Itinerary() {
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime value) { this.createdAt = value; }

    public String getDescription() { return description; }
    public void setDescription(String value) { this.description = value; }

    public long getDestinationId() { return destinationId; }
    public void setDestinationId(long value) { this.destinationId = value; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime value) { this.endDate = value; }

    public long getId() { return id; }
    public void setId(long value) { this.id = value; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime value) { this.startDate = value; }

    public Long getStatus() { return status; }
    public void setStatus(Long value) { this.status = value; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime value) { this.updatedAt = value; }

    public long getUserId() { return userId; }
    public void setUserId(long value) { this.userId = value; }
}
