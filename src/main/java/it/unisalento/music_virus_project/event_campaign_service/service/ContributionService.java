package it.unisalento.music_virus_project.event_campaign_service.service;

import it.unisalento.music_virus_project.event_campaign_service.dto.contribution.ContributionCreateRequest;
import it.unisalento.music_virus_project.event_campaign_service.dto.contribution.ContributionResponse;

import java.util.List;

public interface ContributionService {
    ContributionResponse contribute(ContributionCreateRequest request);
    List<ContributionResponse> findByEvent(String eventId);
    List<ContributionResponse> findByFan(String fanId);
}
