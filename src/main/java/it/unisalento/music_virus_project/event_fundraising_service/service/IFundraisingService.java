package it.unisalento.music_virus_project.event_fundraising_service.service;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Role;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
import it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising.*;

import java.math.BigDecimal;
import java.time.Instant;

public interface IFundraisingService {
    FundraisingResponseDTO getFundraisingById(String fundraisingId);
    FundraisingListResponseDTO getFundraisingsByArtistId(String artistId);
    FundraisingListResponseDTO getOthersFundraisings(String artistId);
    FundraisingListResponseDTO getFundraisingsByVenueId(String venueId);
    FundraisingListResponseDTO getFundraisingsByStatus(FundraisingStatus status);
    FundraisingListResponseDTO getFundraisingsByArtistIdAndStatus(String artistId, FundraisingStatus status);
    FundraisingListResponseDTO getFundraisingsByVenueIdAndStatus(String venueId, FundraisingStatus status);
    FundraisingListResponseDTO getFundraisingsByEventDate(Instant eventDate);
    FundraisingResponseDTO createFundraising(FundraisingCreateRequestDTO request);
    FundraisingResponseDTO updateFundraising(String fundraisingId, FundraisingUpdateRequestDTO request);
    FundraisingResponseDTO disableFundraisingById(String artistId, String fundraisingId);
    FundraisingListResponseDTO disableFundraisingsByUserId(String userId, Role role);
    FundraisingResponseDTO addContributionToFundraising(String fundraisingId, BigDecimal amount);
    FundraisingResponseDTO confirmFundraisingById(String artistId, String fundraisingId);
    FundraisingResponseDTO addVenuePromotionToFundraising(String fundraisingId, VenuePromotionRequestDTO request);
}
