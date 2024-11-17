package org.example.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.protobuf.Enum;
import lombok.Data;
import org.example.api.enums.ItineraryStatus;

import java.time.LocalDateTime;

@Data
public class ItineraryDTO {
    /**
     * 目的地
     */
    private String destinationName;
    /**
     * 行程描述
     */
    private String destinationDescription;
    /**
     * 开始日期
     */
    @JsonProperty("start_date")
    private LocalDateTime startDate;
    /**
     * 结束日期
     */
    @JsonProperty("end_date")
    private LocalDateTime endDate;

    private int status;

}
