package org.example.itineraryservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.itineraryservice.pojo.entity.Itinerary;

import java.util.List;

@Mapper
public interface ItineraryMapper {

    void saveItinerary(Itinerary itinerary);

    List<Itinerary> pageListItinerariesById(Long id, int page, int size);
    List<Itinerary> listItinerariesById(Long id);
    Itinerary getItinerariesById(Long id);

    void updateItinerary(Itinerary itinerary);

    void deleteItinerary(Long id);
}
