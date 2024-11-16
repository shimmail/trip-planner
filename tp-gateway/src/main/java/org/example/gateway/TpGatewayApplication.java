package org.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"org.example.gateway", "org.example.common"})
public class TpGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpGatewayApplication.class, args);
    }

}
