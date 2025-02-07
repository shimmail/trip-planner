package org.example.destinationservice.service.impl;
import org.example.common.exception.DestinationException;
import org.example.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.example.destinationservice.mapper.DestinationMapper;
import org.example.destinationservice.pojo.entity.Destination;
import org.example.destinationservice.service.DestinationService;
import org.springframework.stereotype.Service;
import org.example.api.dto.DestinationDTO;

import java.time.LocalDateTime;

@Service
@Slf4j

public class DestinationServiceImpl implements DestinationService {
    private final DestinationMapper destinationMapper;

    public DestinationServiceImpl(DestinationMapper destinationMapper) {
        this.destinationMapper = destinationMapper;
    }

    @Override
    public Result<DestinationDTO> getDestinationsByName(String name) throws Exception {

        try {
            Destination destination = destinationMapper.getDestinationsByName(name);
            if (destination == null) {
                throw new DestinationException("目的地不存在");
            }
            DestinationDTO destinationDTO = new DestinationDTO(LocalDateTime.now(), destination.getDescription(), destination.getId(), destination.getLocation(), destination.getName(), LocalDateTime.now());
            return Result.success(destinationDTO);
        }catch (DestinationException e){
            throw new DestinationException(e.getMessage());
        }
        catch (Exception e) {
            throw new Exception("查询目的地异常", e);
        }

    }

    @Override
    public Result<DestinationDTO> getDestinationsById(Long id) throws Exception {
        try {
            Destination destination = destinationMapper.getDestinationsById(id);
            if (destination == null) {
                throw new DestinationException("目的地不存在");
            }

            DestinationDTO destinationDTO = new DestinationDTO();
            destinationDTO.setId(destination.getId());
            destinationDTO.setName(destination.getName());
            destinationDTO.setDescription(destination.getDescription());
            destinationDTO.setLocation(destination.getLocation());
            destinationDTO.setCreatedAt(destination.getCreatedAt());
            destinationDTO.setUpdatedAt(destination.getUpdatedAt());

            return Result.success(destinationDTO);
        }catch (DestinationException e){
            throw new DestinationException(e.getMessage());
        }
        catch (Exception e) {
            throw new Exception("查询目的地异常", e);
        }
    }
}
