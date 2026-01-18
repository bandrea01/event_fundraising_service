package it.unisalento.music_virus_project.event_fundraising_service.controllers;

import it.unisalento.music_virus_project.event_fundraising_service.dto.event.*;
import it.unisalento.music_virus_project.event_fundraising_service.service.IEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/event-fundraising/event")
@Validated
public class EventController {

    private final IEventService IEventService;

    public EventController(IEventService IEventService) {
        this.IEventService = IEventService;
    }

    @GetMapping()
    public ResponseEntity<EventListResponseDTO> getAllEvents() {
        var response = IEventService.getAllEvents();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> getEventById(@PathVariable String eventId) {
        var response = IEventService.getEventById(eventId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/fundraising/{fundraisingId}")
    public ResponseEntity<EventResponseDTO> getEventByFundraisingId(@PathVariable String fundraisingId) {
        var response = IEventService.getEventByFundraisingId(fundraisingId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<EventListResponseDTO> getEventsByArtistId(@PathVariable String artistId) {
        var response = IEventService.getEventsByArtistId(artistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/venue/{venueId}")
    public ResponseEntity<EventListResponseDTO> getEventsByVenueId(@PathVariable String venueId) {
        var response = IEventService.getEventsByVenueId(venueId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = "status")
    public ResponseEntity<EventListResponseDTO> getEventsByStatus(@RequestParam String status) {
        var response = IEventService.getEventsByStatus(status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params= "eventDate")
    public ResponseEntity<EventListResponseDTO> getEventsByEventDate(@RequestParam Instant eventDate) {
        var response = IEventService.getEventsByDate(eventDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"startDate", "endDate"})
    public ResponseEntity<EventListResponseDTO> getEventsByDateRange(@RequestParam Instant startDate, @RequestParam Instant endDate) {
        var response = IEventService.getEventsByDateRange(startDate, endDate);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/venues/count")
    public ResponseEntity<EventVenueCounterListResponseDTO> getEventCountersByVenue() {
        var response = IEventService.getEventVenueCounter();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{eventId}/feedbacks")
    public ResponseEntity<FeedbackListResponseDTO> getEventFeedbacks(@PathVariable String eventId) {
        var response = IEventService.getEventFeedbacks(eventId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{eventId}/feedbacks")
    public ResponseEntity<FeedbackResponseDTO> addEventFeedback(@PathVariable String eventId, @RequestBody FeedbackCreateRequestDTO feedbackRequest) {
        var response = IEventService.addEventFeedback(eventId, feedbackRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{eventId}")
    public ResponseEntity<EventResponseDTO> updateEvent(@PathVariable String eventId, @RequestBody EventUpdateRequestDTO updateRequest) {
        var response = IEventService.updateEvent(eventId, updateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/cancel/{eventId}")
    public ResponseEntity<EventResponseDTO> cancelEventById(@PathVariable String eventId) {
        var response = IEventService.cancelEventById(eventId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
