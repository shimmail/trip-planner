package org.example.itineraryservice.service;


import org.example.common.result.Result;
import org.example.api.dto.ItineraryDTO;

public interface ItineraryService {
    Result saveItinerary(ItineraryDTO itineraryDTO);

    Result listItinerariesById(int page, int size) throws Exception;

    Result updateItinerary(Long id, ItineraryDTO itinerary);

    Result deleteItinerary(Long id) throws Exception;
}
