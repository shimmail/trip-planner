package org.example.destinationservice.pojo.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * destinations
 */
@Data
public class Destination {
    private LocalDateTime createdAt;
    /**
     * 行程描述
     */
    private String description;
    /**
     * ID 编号
     */
    private Long id;
    /**
     * 位置
     */
    private String location;
    /**
     * 目的地名称
     */
    private String name;
    private LocalDateTime updatedAt;

}
