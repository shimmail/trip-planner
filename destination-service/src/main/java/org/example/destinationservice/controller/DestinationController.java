package org.example.destinationservice.controller;

import jdk.nashorn.internal.ir.RuntimeNode;
import org.example.destinationservice.pojo.dto.DestinationDTO;
import org.example.destinationservice.pojo.entity.Destination;
import org.example.destinationservice.result.Result;
import org.example.destinationservice.service.DestinationService;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.callback.PrivateKeyCallback;
import java.util.List;

@RestController
@RequestMapping("/destination")
public class DestinationController {
    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping("/getDestinationsById")
    public Result listDestinations(@RequestBody DestinationDTO destinationDTO) {
        return destinationService.getDestinationsById(destinationDTO);
    }
}