package org.example.destinationservice.service.impl;
import org.apache.ibatis.annotations.Mapper;
import org.example.destinationservice.mapper.DestinationMapper;
import org.example.destinationservice.pojo.dto.DestinationDTO;
import org.example.destinationservice.pojo.entity.Destination;
import org.example.destinationservice.result.Result;
import org.example.destinationservice.service.DestinationService;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.crypto.Des;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class DestinationServiceImpl implements DestinationService {
    private final DestinationMapper destinationMapper;

    public DestinationServiceImpl(DestinationMapper destinationMapper) {
        this.destinationMapper = destinationMapper;
    }

    @Override
    public Result getDestinationsById(DestinationDTO destinationDTO) {
        long id=destinationDTO.getId();
        Destination destination = destinationMapper.getDestinationsById(id);
        if (destination==null){
            return Result.error("目的地不存在");
        }
        return Result.success(destinationMapper.getDestinationsById(id));
    }
}
