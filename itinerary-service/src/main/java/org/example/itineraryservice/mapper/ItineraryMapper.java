package org.example.itineraryservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.itineraryservice.pojo.entity.Itinerary;

import java.util.List;

@Mapper
public interface ItineraryMapper {

    void saveItinerary(Itinerary itinerary);

    List<Itinerary> listItinerariesById(long id, int page, int size);

    Itinerary getItinerariesById(long id);
}
