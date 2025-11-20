package it.unisalento.music_virus_project.event_campaign_service.controllers;

import it.unisalento.music_virus_project.event_campaign_service.dto.event.EventCreateRequest;
import it.unisalento.music_virus_project.event_campaign_service.dto.event.EventResponse;
import it.unisalento.music_virus_project.event_campaign_service.dto.event.EventUpdateRequest;
import it.unisalento.music_virus_project.event_campaign_service.service.EventService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@Validated
public class EventController {
//
//    private final EventService eventService;
//
//    public EventController(EventService eventService) {
//        this.eventService = eventService;
//    }
//
//    @PostMapping
//    public ResponseEntity<EventResponse> createEvent(
//            @RequestHeader("X-Artist-Id") String artistId,
//            @Valid @RequestBody EventCreateRequest request
//    ) {
//        request.setArtistId(artistId); // forza coerenza con header
//        EventResponse created = eventService.create(request);
//        return ResponseEntity.status(HttpStatus.CREATED).body(created);
//    }
//
//    @PutMapping("/{eventId}")
//    public ResponseEntity<EventResponse> updateEvent(
//            @PathVariable String eventId,
//            @RequestHeader("X-Artist-Id") String artistId,
//            @Valid @RequestBody EventUpdateRequest request
//    ) {
//        EventResponse updated = eventService.update(eventId, artistId, request);
//        return ResponseEntity.ok(updated);
//    }
//
//    @DeleteMapping("/{eventId}")
//    public ResponseEntity<Void> deleteDraft(
//            @PathVariable String eventId,
//            @RequestHeader("X-Artist-Id") String artistId
//    ) {
//        eventService.deleteDraft(eventId, artistId);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PostMapping("/{eventId}/publish")
//    public ResponseEntity<EventResponse> publish(
//            @PathVariable String eventId,
//            @RequestHeader("X-Artist-Id") String artistId
//    ) {
//        EventResponse published = eventService.publish(eventId, artistId);
//        return ResponseEntity.ok(published);
//    }
//
//    @GetMapping("/{eventId}")
//    public ResponseEntity<EventResponse> findById(@PathVariable String eventId) {
//        return ResponseEntity.ok(eventService.findById(eventId));
//    }
//
//    @GetMapping("/live")
//    public ResponseEntity<List<EventResponse>> listLiveAfter(
//            @RequestParam(name = "after", required = false)
//            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant after
//    ) {
//        Instant pivot = (after == null) ? Instant.now() : after;
//        return ResponseEntity.ok(eventService.findLiveAfter(pivot));
//    }
//
//    @GetMapping("/artist/{artistId}")
//    public ResponseEntity<List<EventResponse>> listByArtist(@PathVariable String artistId) {
//        return ResponseEntity.ok(eventService.findByArtist(artistId));
//    }
}
