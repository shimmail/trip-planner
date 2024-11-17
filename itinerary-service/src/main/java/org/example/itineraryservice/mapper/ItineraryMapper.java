package org.example.itineraryservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.itineraryservice.pojo.entity.Itinerary;

import java.util.List;

@Mapper
public interface ItineraryMapper {

    void saveItinerary(Itinerary itinerary);

    List<Itinerary> pageListItinerariesById(long id, int page, int size);
    List<Itinerary> listItinerariesById(long id);
    Itinerary getItinerariesById(long id);

    void updateItinerary(Itinerary itinerary);
}
