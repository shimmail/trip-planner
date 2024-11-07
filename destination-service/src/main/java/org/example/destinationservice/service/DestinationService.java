package org.example.destinationservice.service;


import org.example.destinationservice.pojo.dto.DestinationDTO;
import org.example.destinationservice.pojo.entity.Destination;
import org.example.destinationservice.result.Result;

import java.util.List;

public interface DestinationService {
    Result getDestinationsById(DestinationDTO destinationDTO);
}
