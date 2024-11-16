package org.example.destinationservice.controller;


import org.example.common.result.Result;
import org.example.api.dto.DestinationDTO;
import org.example.destinationservice.pojo.entity.Destination;
import org.example.destinationservice.service.DestinationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/destination")
public class DestinationController {
    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping("/getDestinationsByName")
    public Result<Destination> getDestinationsByName(@RequestParam String name) {
        try {
            DestinationDTO destinationDTO = new DestinationDTO();
            destinationDTO.setName(name);
            return destinationService.getDestinationsByName(destinationDTO);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}