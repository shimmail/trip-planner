package org.example;

import org.example.api.dto.UserDTO;
import org.example.common.result.Result;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
public class TpGatewayTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testLoginRateLimit() {
        // 发起超过最大次数的请求
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("String");
        userDTO.setPassword("String");
        for (int i = 0; i < 6; i++) {

            ResponseEntity<Result> response = restTemplate.postForEntity("/user/login", userDTO, Result.class);
            if (i < 5) {
                assertEquals(HttpStatus.OK, response.getStatusCode());
            } else {
                assertEquals(HttpStatus.TOO_MANY_REQUESTS, response.getStatusCode());
            }
        }
    }
}
