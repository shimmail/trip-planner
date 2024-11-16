package org.example.destinationservice.service;

import org.example.common.result.Result;
import org.example.api.dto.DestinationDTO;
import org.example.destinationservice.pojo.entity.Destination;

public interface DestinationService {

    Result<Destination> getDestinationsByName(DestinationDTO destinationDTO) throws Exception;
}
