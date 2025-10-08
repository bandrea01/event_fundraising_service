package it.unisalento.music_virus_project.event_campaign_service.controllers;

import it.unisalento.music_virus_project.event_campaign_service.dto.tier.TierCreateRequest;
import it.unisalento.music_virus_project.event_campaign_service.dto.tier.TierResponse;
import it.unisalento.music_virus_project.event_campaign_service.service.TierService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiers")
@Validated
public class TierController {

    private final TierService tierService;

    public TierController(TierService tierService) {
        this.tierService = tierService;
    }

    @PostMapping
    public ResponseEntity<TierResponse> create(
            @RequestHeader("X-Artist-Id") String artistId,
            @Valid @RequestBody TierCreateRequest request
    ) {
        TierResponse created = tierService.create(request, artistId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<TierResponse>> listByEvent(@PathVariable String eventId) {
        return ResponseEntity.ok(tierService.listByEvent(eventId));
    }
}
