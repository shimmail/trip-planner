package org.example.itineraryservice.controller;

import org.example.common.result.Result;
import org.example.api.dto.ItineraryDTO;
import org.example.itineraryservice.service.ItineraryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/itinerary")
public class ItineraryController {
    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @PostMapping("/saveItinerary")
    public Result saveItinerary(@RequestBody ItineraryDTO itineraryDTO,
                                @RequestHeader("Authorization") String token){
        return itineraryService.saveItinerary(itineraryDTO,token);
    }

    @GetMapping("/listItinerariesById")
    public Result listItineraryByIds( @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer size,
                                      @RequestHeader("Authorization") String token) {
        try {
            return itineraryService.listItinerariesById(page-1, size,token);
        } catch (IllegalArgumentException e) {
            return Result.error("参数错误");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/updateItinerary")
    public Result updateItinerary(@RequestParam long id,
                                  @RequestBody ItineraryDTO itineraryDTO,
                                  @RequestHeader("Authorization") String token) throws Exception {
        return itineraryService.updateItinerary(id,itineraryDTO,token);
    }

    @DeleteMapping("/deleteItinerary")
    public Result deleteItinerary(@RequestParam long id,
                                  @RequestHeader("Authorization") String token) throws Exception {
        return itineraryService.deleteItinerary(id,token);
    }
}