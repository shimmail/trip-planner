package org.example.destinationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.common.result.Result;
import org.example.api.dto.DestinationDTO;
import org.example.destinationservice.pojo.entity.Destination;
import org.example.destinationservice.service.DestinationService;
import org.example.frequencycontrolstarter.annotation.FrequencyAnnotation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/destination")
public class DestinationController {
    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @FrequencyAnnotation(key = "getDestinationsByName", maxCount = 5, timeRange = 10, timeUnit = "SECONDS")
    @GetMapping("/getDestinationsByName")
    public Result<DestinationDTO> getDestinationsByName(@RequestParam String name) {
        try {
            return destinationService.getDestinationsByName(name);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
    @GetMapping("/getDestinationsById")
    public Result<DestinationDTO> getDestinationsById(@RequestParam Long id){
        try {
            return destinationService.getDestinationsById(id);
        }catch (Exception e){
            return Result.error(e.getMessage());
        }
    }
}