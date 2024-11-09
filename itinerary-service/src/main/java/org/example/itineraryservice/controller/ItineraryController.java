package org.example.itineraryservice.controller;

import com.example.common.result.Result;
import org.example.itineraryservice.pojo.dto.ItineraryDTO;
import org.example.itineraryservice.pojo.entity.Itinerary;
import org.example.itineraryservice.service.ItineraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/itinerary")
public class ItineraryController {
    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @PostMapping("/saveItinerary")
    public Result saveItinerary(@RequestBody ItineraryDTO itineraryDTO){
        return itineraryService.saveItinerary(itineraryDTO);
    }

    @GetMapping("/listItinerariesById")
    public Result listItineraryByIds( @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size) {
        try {
            return itineraryService.listItinerariesById(page-1, size);
        } catch (IllegalArgumentException e) {
            return Result.error("参数错误");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}