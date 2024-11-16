package org.example.itineraryservice.service.impl;

import org.example.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.example.api.client.DestinationClient;
import org.example.api.dto.DestinationDTO;
import org.example.common.utils.JwtUtil;
import org.example.itineraryservice.mapper.ItineraryMapper;
import org.example.itineraryservice.pojo.dto.ItineraryDTO;

import org.example.itineraryservice.pojo.entity.Itinerary;
import org.example.itineraryservice.pojo.vo.ItineraryVo;
import org.example.itineraryservice.service.ItineraryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

public class ItineraryServiceImpl implements ItineraryService {
    private final DestinationClient destinationClient;
    private final ItineraryMapper itineraryMapper;
    private final JwtUtil jwtUtil;

    public ItineraryServiceImpl(DestinationClient destinationClient, ItineraryMapper itineraryMapper, JwtUtil jwtUtil) {
        this.destinationClient = destinationClient;
        this.itineraryMapper = itineraryMapper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Result saveItinerary(ItineraryDTO itineraryDTO, String token) {
        try {
            long userId = jwtUtil.getId(token);
            itineraryMapper.saveItinerary();
            Result<DestinationDTO> destinations = destinationClient.getDestinationsByName(itineraryDTO.getName());
            if (destinations.getCode() == 0) {
                return Result.error(destinations.getMsg());
            }
            long destinationId = destinations.getData().getId();


            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @Override
    public Result listItinerariesById(int page, int size, String token) throws Exception {
        try {
            long id = jwtUtil.getId(token);

            // 调用 Mapper 获取行程信息

            Itinerary i = itineraryMapper.getItinerariesById(id);

            List<Itinerary> itineraries = itineraryMapper.listItinerariesById(id, page, size);
            List<ItineraryVo> itineraryVos = itineraries.stream().map(itinerary -> {
                ItineraryVo vo = new ItineraryVo();
                vo.setCreatedAt(itinerary.getCreatedAt());
                vo.setDescription(itinerary.getDescription());
                vo.setEndDate(itinerary.getEndDate());
                // 调用目的地模块获得行程目的地和目的地位置
                vo.setLocation(null);
                vo.setName(null);
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
}
