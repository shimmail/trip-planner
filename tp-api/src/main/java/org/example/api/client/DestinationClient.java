package org.example.api.client;

import org.example.common.result.Result;
import org.example.api.dto.DestinationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("destination-service")
@Component
public interface DestinationClient {
    @GetMapping("/destination/getDestinationsByName")
    Result <DestinationDTO> getDestinationsByName(@RequestParam String name);

    @GetMapping("/destination/getDestinationsById")
    Result <DestinationDTO> getDestinationsById(long id);
}
