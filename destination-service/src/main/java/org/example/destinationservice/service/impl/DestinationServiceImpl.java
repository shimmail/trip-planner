package org.example.destinationservice.service.impl;

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
                return Result.error("目的地不存在");
            }
            return Result.success(destination);
        } catch (Exception e) {

            log.error("获取目的地时发生错误", e);

            throw new Exception("目的地不存在", e);
        }

    }

}
