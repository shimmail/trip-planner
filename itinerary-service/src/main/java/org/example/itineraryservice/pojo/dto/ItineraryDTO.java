package org.example.itineraryservice.pojo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ItineraryDTO {
    /**
     * 目的地
     */
    private String name;
    /**
     * 行程描述
     */
    private String description;
    /**
     * 开始日期
     */
    private LocalDateTime startDate;
    /**
     * 结束日期
     */
    private LocalDateTime endDate;


}
