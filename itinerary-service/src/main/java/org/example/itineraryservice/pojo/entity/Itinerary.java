package org.example.itineraryservice.pojo.entity;
import lombok.Data;
import lombok.Setter;
import org.example.api.enums.ItineraryStatus;

import java.time.LocalDateTime;

/**
 * itineraries
 */
@Data
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
    private int status;


    private LocalDateTime updatedAt;
    /**
     * 外键，关联用户表
     */
    private long userId;

    public Itinerary(LocalDateTime createdAt, String description, long destinationId, LocalDateTime endDate, LocalDateTime startDate, LocalDateTime updatedAt, long userId) {
        this.createdAt = createdAt;
        this.description = description;
        this.destinationId = destinationId;
        this.endDate = endDate;
        this.startDate = startDate;
        this.updatedAt = updatedAt;
        this.userId = userId;
        this.id = idCounter++;
        this.status = ItineraryStatus.TODO.getCode();
    }

    public Itinerary() {
    }

}
