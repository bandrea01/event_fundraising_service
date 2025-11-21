package it.unisalento.music_virus_project.event_fundraising_service.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/venue-requests")
@Validated
public class VenueRequestController {
//
//    private final VenueRequestService venueRequestService;
//
//    public VenueRequestController(VenueRequestService venueRequestService) {
//        this.venueRequestService = venueRequestService;
//    }
//
//    @PostMapping
//    public ResponseEntity<VenueRequestResponse> create(
//            @RequestHeader("X-Artist-Id") String artistId,
//            @Valid @RequestBody VenueRequestCreateRequest request
//    ) {
//        return ResponseEntity.ok(venueRequestService.create(request, artistId));
//    }
//
//    @PutMapping("/{venueRequestId}/decision")
//    public ResponseEntity<VenueRequestResponse> decide(
//            @PathVariable String venueRequestId,
//            @RequestHeader("X-Venue-Id") String venueId,
//            @Valid @RequestBody VenueRequestDecisionRequest request
//    ) {
//        return ResponseEntity.ok(venueRequestService.decide(venueRequestId, venueId, request));
//    }
//
//    @GetMapping("/venue/{venueId}")
//    public ResponseEntity<List<VenueRequestResponse>> listByVenueAndStatus(
//            @PathVariable String venueId,
//            @RequestParam(name = "status", defaultValue = "PENDING") String status
//    ) {
//        return ResponseEntity.ok(venueRequestService.listByVenueAndStatus(venueId, status));
//    }
//
//    @GetMapping("/event/{eventId}")
//    public ResponseEntity<List<VenueRequestResponse>> listByEvent(@PathVariable String eventId) {
//        return ResponseEntity.ok(venueRequestService.listByEvent(eventId));
//    }
}
