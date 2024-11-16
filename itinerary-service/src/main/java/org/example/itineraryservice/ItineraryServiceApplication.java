package org.example.itineraryservice;

import org.example.api.client.DestinationClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(scanBasePackages = {"org.example.itineraryservice", "org.example.common"})
@EnableFeignClients(clients = {DestinationClient.class})
@MapperScan("org.example.itineraryservice.mapper")
public class ItineraryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItineraryServiceApplication.class, args);
    }

}
