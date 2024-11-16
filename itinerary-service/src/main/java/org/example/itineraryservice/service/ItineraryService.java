package org.example.itineraryservice.service;


import org.example.common.result.Result;
import org.example.itineraryservice.pojo.dto.ItineraryDTO;

public interface ItineraryService {
    Result saveItinerary(ItineraryDTO itineraryDTO, String token);

    Result listItinerariesById(int page, int size, String token) throws Exception;
}
