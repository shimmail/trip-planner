package org.example.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * destinations
 */
@Data
public class DestinationDTO {
    public DestinationDTO(LocalDateTime createdAt, String description, Long id, String location, String name, LocalDateTime updatedAt) {
        this.createdAt = createdAt;
        this.description = description;
        this.id = id;
        this.location = location;
        this.name = name;
        this.updatedAt = updatedAt;
    }

    private LocalDateTime createdAt;
    /**
     * 行程描述
     */
    private String description;
    /**
     * 目的地id编号
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

    public DestinationDTO() {

    }

}
