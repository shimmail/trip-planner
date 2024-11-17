package org.example.itineraryservice.pojo.vo;

import lombok.Data;
import org.example.api.enums.ItineraryStatus;

import java.time.LocalDateTime;
@Data
public class ItineraryVo {
        /**
         * 目的地名称
         */
        private String name;
        /**
         * 位置
         */
        private String location;
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
        /**
         * 行程状态：0=待办，1=已完成
         */
        private int status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ItineraryVo() {
        }

        public ItineraryVo(String name, String location, String description, LocalDateTime startDate, LocalDateTime endDate, int status, LocalDateTime createdAt, LocalDateTime updatedAt) {
                this.name = name;
                this.location = location;
                this.description = description;
                this.startDate = startDate;
                this.endDate = endDate;
                this.status = status;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
        }

}

