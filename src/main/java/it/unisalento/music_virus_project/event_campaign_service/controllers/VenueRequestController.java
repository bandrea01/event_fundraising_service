package it.unisalento.music_virus_project.event_campaign_service.controllers;

import it.unisalento.music_virus_project.event_campaign_service.dto.venue.VenueRequestCreateRequest;
import it.unisalento.music_virus_project.event_campaign_service.dto.venue.VenueRequestDecisionRequest;
import it.unisalento.music_virus_project.event_campaign_service.dto.venue.VenueRequestResponse;
import it.unisalento.music_virus_project.event_campaign_service.service.VenueRequestService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venue-requests")
@Validated
public class VenueRequestController {

    private final VenueRequestService venueRequestService;

    public VenueRequestController(VenueRequestService venueRequestService) {
        this.venueRequestService = venueRequestService;
    }

    @PostMapping
    public ResponseEntity<VenueRequestResponse> create(
            @RequestHeader("X-Artist-Id") String artistId,
            @Valid @RequestBody VenueRequestCreateRequest request
    ) {
        return ResponseEntity.ok(venueRequestService.create(request, artistId));
    }

    @PutMapping("/{venueRequestId}/decision")
    public ResponseEntity<VenueRequestResponse> decide(
            @PathVariable String venueRequestId,
            @RequestHeader("X-Venue-Id") String venueId,
            @Valid @RequestBody VenueRequestDecisionRequest request
    ) {
        return ResponseEntity.ok(venueRequestService.decide(venueRequestId, venueId, request));
    }

    @GetMapping("/venue/{venueId}")
    public ResponseEntity<List<VenueRequestResponse>> listByVenueAndStatus(
            @PathVariable String venueId,
            @RequestParam(name = "status", defaultValue = "PENDING") String status
    ) {
        return ResponseEntity.ok(venueRequestService.listByVenueAndStatus(venueId, status));
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<VenueRequestResponse>> listByEvent(@PathVariable String eventId) {
        return ResponseEntity.ok(venueRequestService.listByEvent(eventId));
    }
}
