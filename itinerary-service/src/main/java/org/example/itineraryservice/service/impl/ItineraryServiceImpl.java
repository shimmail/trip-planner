package org.example.itineraryservice.service.impl;
import org.example.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.example.api.client.DestinationClient;
import org.example.api.dto.DestinationDTO;
import org.example.common.utils.JwtUtil;
import org.example.common.utils.UserContext;
import org.example.itineraryservice.mapper.ItineraryMapper;
import org.example.api.dto.ItineraryDTO;

import org.example.itineraryservice.pojo.entity.Itinerary;
import org.example.itineraryservice.pojo.vo.ItineraryVo;
import org.example.itineraryservice.service.ItineraryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j

public class ItineraryServiceImpl implements ItineraryService {
    private final DestinationClient destinationClient;
    private final ItineraryMapper itineraryMapper;
    public ItineraryServiceImpl(DestinationClient destinationClient, ItineraryMapper itineraryMapper, JwtUtil jwtUtil) {
        this.destinationClient = destinationClient;
        this.itineraryMapper = itineraryMapper;
    }

    // 创建行程
    @Override
    public Result saveItinerary(ItineraryDTO itineraryDTO) {
        try {
            Long userId = UserContext.getUser();
            String name = itineraryDTO.getDestinationName();
            if (name == null || name.trim().isEmpty()) {
                return Result.error("目的地不可为空");
            }
            Result<DestinationDTO> destinations = destinationClient.getDestinationsByName(name);
            if (destinations.getCode() == 0) {
                return Result.error(destinations.getMsg());
            }
            Long destinationId = destinations.getData().getId();

            Itinerary itinerary = new Itinerary(LocalDateTime.now(), itineraryDTO.getDestinationDescription(), destinationId, itineraryDTO.getEndDate(), itineraryDTO.getStartDate(), LocalDateTime.now(), userId);
            itineraryMapper.saveItinerary(itinerary);
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    // 查询行程
    @Override
    public Result listItinerariesById(int page, int size) throws Exception {
        try {
            long id = UserContext.getUser();
            // 调用 Mapper 获取行程信息
            Itinerary i = itineraryMapper.getItinerariesById(id);

            List<Itinerary> itineraries = itineraryMapper.pageListItinerariesById(id, page, size);
            List<ItineraryVo> itineraryVos = itineraries.stream().map(itinerary -> {
                ItineraryVo vo = new ItineraryVo();
                vo.setCreatedAt(itinerary.getCreatedAt());
                vo.setDescription(itinerary.getDescription());
                vo.setEndDate(itinerary.getEndDate());

                // 调用目的地模块获得行程目的地
                long d_id = itinerary.getDestinationId();
                Result<DestinationDTO> destinationDTOResult = null;
                try {
                    destinationDTOResult = destinationClient.getDestinationsById(d_id);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                DestinationDTO destinationDTO = destinationDTOResult.getData();
                vo.setLocation(destinationDTO.getLocation());
                vo.setName(destinationDTO.getName());
                vo.setStartDate(itinerary.getStartDate());
                vo.setStatus(itinerary.getStatus());
                vo.setUpdatedAt(itinerary.getUpdatedAt());
                return vo;
            }).collect(Collectors.toList());

            // 封装结果并返回
            return Result.success(itineraryVos);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Result updateItinerary(Long id, ItineraryDTO itineraryDTO) {
        try {
            Long destinationId = null;

            Long userId = UserContext.getUser();
            String name = itineraryDTO.getDestinationName();
            if (name != null) {
                Result<DestinationDTO> destinations = destinationClient.getDestinationsByName(name);
                if (destinations.getCode() == 0) {
                    return Result.error(destinations.getMsg());
                }
                destinationId = destinations.getData().getId();
            }


            List<Itinerary> itineraries = itineraryMapper.listItinerariesById(userId);
            Optional<Itinerary> optionalItinerary = itineraries.stream()
                    .filter(itinerary -> itinerary.getId() == id)
                    .findFirst();

            if (optionalItinerary.isPresent()) {
                Itinerary itinerary = optionalItinerary.get();
                itinerary.setDescription(itineraryDTO.getDestinationDescription());
                itinerary.setDestinationId(destinationId);
                itinerary.setStartDate(itineraryDTO.getStartDate());
                itinerary.setEndDate(itineraryDTO.getEndDate());
                itinerary.setUpdatedAt(LocalDateTime.now());
                itinerary.setUserId(userId);
                itinerary.setStatus(itineraryDTO.getStatus());
                itineraryMapper.updateItinerary(itinerary);
                return Result.success();
            }
            else {
                return Result.error("行程不存在");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Result deleteItinerary(Long id) throws Exception {
        try {
            Long userId = UserContext.getUser();
            List<Itinerary> itineraries = itineraryMapper.listItinerariesById(userId);
            Optional<Itinerary> optionalItinerary = itineraries.stream()
                    .filter(itinerary -> itinerary.getId() == id)
                    .findFirst();
            if (optionalItinerary.isPresent()) {
                itineraryMapper.deleteItinerary(id);
                return Result.success();
            }
            else {
                return Result.error("行程不存在");
            }

        } catch (Exception e) {
           throw new RuntimeException(e.getMessage());
        }

    }
}
