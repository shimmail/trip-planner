package org.example.itineraryservice.service;


import org.example.common.result.Result;
import org.example.api.dto.ItineraryDTO;

public interface ItineraryService {
    Result saveItinerary(ItineraryDTO itineraryDTO, String token);

    Result listItinerariesById(int page, int size, String token) throws Exception;

    Result updateItinerary(long id, ItineraryDTO itinerary, String token);

    Result deleteItinerary(long id, String token) throws Exception;
}
