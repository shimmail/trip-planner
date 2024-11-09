package org.example.itineraryservice.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;
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
        private Long status;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ItineraryVo() {
        }

        public ItineraryVo(String name, String location, String description, LocalDateTime startDate, LocalDateTime endDate, Long status, LocalDateTime createdAt, LocalDateTime updatedAt) {
                this.name = name;
                this.location = location;
                this.description = description;
                this.startDate = startDate;
                this.endDate = endDate;
                this.status = status;
                this.createdAt = createdAt;
                this.updatedAt = updatedAt;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getLocation() {
                return location;
        }

        public void setLocation(String location) {
                this.location = location;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public LocalDateTime getStartDate() {
                return startDate;
        }

        public void setStartDate(LocalDateTime startDate) {
                this.startDate = startDate;
        }

        public LocalDateTime getEndDate() {
                return endDate;
        }

        public void setEndDate(LocalDateTime endDate) {
                this.endDate = endDate;
        }

        public Long getStatus() {
                return status;
        }

        public void setStatus(Long status) {
                this.status = status;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
                return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
        }
}

