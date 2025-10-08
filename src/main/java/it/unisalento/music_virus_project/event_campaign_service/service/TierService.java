package it.unisalento.music_virus_project.event_campaign_service.service;

import it.unisalento.music_virus_project.event_campaign_service.dto.tier.TierCreateRequest;
import it.unisalento.music_virus_project.event_campaign_service.dto.tier.TierResponse;

import java.util.List;

public interface TierService {
    TierResponse create(TierCreateRequest request, String requesterArtistId);
    List<TierResponse> listByEvent(String eventId);
}
