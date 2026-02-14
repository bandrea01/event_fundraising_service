package it.unisalento.music_virus_project.event_fundraising_service.controllers;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Role;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.*;
import it.unisalento.music_virus_project.event_fundraising_service.service.IFundraisingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/event-fundraising/fundraising")
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
    public ResponseEntity<FundraisingListResponseDTO> getOthersFundraisings(
            @AuthenticationPrincipal Jwt principal
    ) {
        String artistId = principal.getClaimAsString("userId");
        var response = IFundraisingService.getOthersFundraisings(artistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = "artistId")
    public ResponseEntity<FundraisingListResponseDTO> getFundraisingsByArtistId(@RequestParam String artistId) {
        var response = IFundraisingService.getFundraisingsByArtistId(artistId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ARTIST')")
    @GetMapping("/me")
    public ResponseEntity<FundraisingListResponseDTO> getPersonalFundraising(
            @AuthenticationPrincipal Jwt principal) {
        String userId = principal.getClaimAsString("userId");
        Role role = Role.valueOf(principal.getClaimAsString("role").substring(5));
        if (role == Role.ARTIST) {
            FundraisingListResponseDTO response = IFundraisingService.getFundraisingsByArtistId(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else if (role == Role.VENUE) {
            FundraisingListResponseDTO response = IFundraisingService.getFundraisingsByVenueId(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new FundraisingListResponseDTO(), HttpStatus.OK);
        }
    }

    @GetMapping(params = "venueId")
    public ResponseEntity<FundraisingListResponseDTO> getFundraisingsByVenueId(@RequestParam String venueId) {
        FundraisingListResponseDTO response = IFundraisingService.getFundraisingsByVenueId(venueId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = "status")
    public ResponseEntity<FundraisingListResponseDTO> getFundraisingsByStatus(@RequestParam FundraisingStatus status) {
        FundraisingListResponseDTO response = IFundraisingService.getFundraisingsByStatus(status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = "eventDate")
    public ResponseEntity<FundraisingListResponseDTO> getFundraisingsByEventDate(
            @RequestParam String eventDate) {
        FundraisingListResponseDTO response = IFundraisingService.getFundraisingsByEventDate(
                java.time.Instant.parse(eventDate));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"artistId", "status"})
    public ResponseEntity<FundraisingListResponseDTO> getFundraisingsByArtistIdAndStatus(
            @RequestParam String artistId,
            @RequestParam FundraisingStatus status) {
        FundraisingListResponseDTO response;
        if (artistId != null && status != null) {
            response = IFundraisingService.getFundraisingsByArtistIdAndStatus(artistId, status);
        } else {
            response = new FundraisingListResponseDTO();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(params = {"venueId", "status"})
    public ResponseEntity<FundraisingListResponseDTO> getFundraisingsByVenueIdAndStatus(
            @RequestParam String venueId,
            @RequestParam FundraisingStatus status) {
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

    @PreAuthorize("hasRole('ROLE_ARTIST')")
    @PatchMapping("/cancel/{fundraisingId}")
    public ResponseEntity<FundraisingResponseDTO> cancelFundraisingById(
            @AuthenticationPrincipal Jwt principal,
            @PathVariable String fundraisingId) {
        String artistId = principal.getClaimAsString("userId");
        FundraisingResponseDTO response = IFundraisingService.disableFundraisingById(artistId, fundraisingId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ARTIST')")
    @PatchMapping("/confirm/{fundraisingId}")
    public ResponseEntity<FundraisingResponseDTO> confirmFundraisingById(
            @AuthenticationPrincipal Jwt principal,
            @PathVariable String fundraisingId) {
        String artistId = principal.getClaimAsString("userId");
        FundraisingResponseDTO response = IFundraisingService.confirmFundraisingById(artistId, fundraisingId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_VENUE')")
    @PatchMapping("/{fundraisingId}/promotion")
    public ResponseEntity<FundraisingResponseDTO> addVenuePromotion(
            @PathVariable String fundraisingId,
            @RequestBody VenuePromotionRequestDTO request
    )
    {
        FundraisingResponseDTO response = IFundraisingService.addVenuePromotionToFundraising(fundraisingId, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
