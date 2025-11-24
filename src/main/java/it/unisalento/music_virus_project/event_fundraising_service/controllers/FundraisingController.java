package it.unisalento.music_virus_project.event_fundraising_service.controllers;

import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingCreateRequestDTO;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingListResponseDTO;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingResponseDTO;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.FundraisingUpdateRequestDTO;
import it.unisalento.music_virus_project.event_fundraising_service.service.IFundraisingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fundraising")
@Validated
public class FundraisingController {

    private final IFundraisingService IFundraisingService;

    public FundraisingController(IFundraisingService IFundraisingService) {
        this.IFundraisingService = IFundraisingService;
    }

    @GetMapping("/{fundraisingId}")
    public ResponseEntity<FundraisingResponseDTO> getFundraisingById(@PathVariable String fundraisingId) {
        var response = IFundraisingService.getFundraisingById(fundraisingId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<FundraisingListResponseDTO> getAllFundraisings() {
        var response = IFundraisingService.getAllFundraisings();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<FundraisingListResponseDTO> getFundraisingsByArtistId(@PathVariable String artistId) {
        var response = IFundraisingService.getFundraisingsByArtistId(artistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/venue/{venueId}")
    public ResponseEntity<FundraisingListResponseDTO> getFundraisingsByVenueId(@PathVariable String venueId) {
        FundraisingListResponseDTO response = IFundraisingService.getFundraisingsByVenueId(venueId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<FundraisingListResponseDTO> getFundraisingsByStatus(@PathVariable FundraisingStatus status) {
        FundraisingListResponseDTO response = IFundraisingService.getFundraisingsByStatus(status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<FundraisingListResponseDTO> getFundraisingsByEventDate(
            @RequestParam String eventDate) {
        FundraisingListResponseDTO response = IFundraisingService.getFundraisingsByEventDate(
                java.time.Instant.parse(eventDate));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<FundraisingListResponseDTO> getFundraisingsByArtistIdAndStatus(
            @RequestParam(required = false) String artistId,
            @RequestParam(required = false) FundraisingStatus status) {
        FundraisingListResponseDTO response;
        if (artistId != null && status != null) {
            response = IFundraisingService.getFundraisingsByArtistIdAndStatus(artistId, status);
        } else {
            response = new FundraisingListResponseDTO();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<FundraisingListResponseDTO> getFundraisingsByVenueIdAndStatus(
            @RequestParam(required = false) String venueId,
            @RequestParam(required = false) FundraisingStatus status) {
        FundraisingListResponseDTO response;
        if (venueId != null && status != null) {
            response = IFundraisingService.getFundraisingsByVenueIdAndStatus(venueId, status);
        } else {
            response = new FundraisingListResponseDTO();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<FundraisingResponseDTO> createFundraising(
            @RequestBody FundraisingCreateRequestDTO request) {
        FundraisingResponseDTO response = IFundraisingService.createFundraising(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{fundraisingId}")
    public ResponseEntity<FundraisingResponseDTO> updateFundraising(
            @PathVariable String fundraisingId,
            @RequestBody FundraisingUpdateRequestDTO request) {
        FundraisingResponseDTO response = IFundraisingService.updateFundraising(fundraisingId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ARTIST')")
    @PatchMapping("/cancel/{fundraisingId}")
    public ResponseEntity<FundraisingResponseDTO> cancelFundraisingById(
            @AuthenticationPrincipal Jwt principal,
            @PathVariable String fundraisingId) {
        String artistId = principal.getClaimAsString("user_id");
        FundraisingResponseDTO response = IFundraisingService.cancelFundraisingById(artistId, fundraisingId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
