package org.example.destinationservice.service.impl;

import org.example.common.exception.DestinationException;
import org.example.common.result.Result;

import lombok.extern.slf4j.Slf4j;
import org.example.destinationservice.mapper.DestinationMapper;
import org.example.destinationservice.pojo.entity.Destination;
import org.example.destinationservice.service.DestinationService;
import org.springframework.stereotype.Service;
import org.example.api.dto.DestinationDTO;
@Service
@Slf4j
public class DestinationServiceImpl implements DestinationService {
    private final DestinationMapper destinationMapper;

    public DestinationServiceImpl(DestinationMapper destinationMapper) {
        this.destinationMapper = destinationMapper;
    }

    @Override
    public Result<Destination> getDestinationsByName(DestinationDTO destinationDTO) throws Exception {

        try {
            Destination destination = destinationMapper.getDestinationsByName(destinationDTO.getName());
            if (destination == null) {
                throw new DestinationException("目的地不存在");
            }
            return Result.success(destination);
        }catch (DestinationException e){
            throw new DestinationException(e.getMessage());
        }
        catch (Exception e) {
            throw new Exception("查询目的地异常", e);
        }

    }

}
