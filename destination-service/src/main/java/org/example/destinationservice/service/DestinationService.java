package org.example.destinationservice.service;

import org.example.common.result.Result;
import org.example.api.dto.DestinationDTO;
import org.example.destinationservice.pojo.entity.Destination;

public interface DestinationService {

    Result<DestinationDTO> getDestinationsByName(String name) throws Exception;

    Result<DestinationDTO> getDestinationsById(long id) throws Exception;
}
