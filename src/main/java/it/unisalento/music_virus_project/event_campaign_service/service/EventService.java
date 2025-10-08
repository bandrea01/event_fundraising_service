package it.unisalento.music_virus_project.event_campaign_service.service;

import it.unisalento.music_virus_project.event_campaign_service.dto.event.EventCreateRequest;
import it.unisalento.music_virus_project.event_campaign_service.dto.event.EventResponse;
import it.unisalento.music_virus_project.event_campaign_service.dto.event.EventUpdateRequest;

import java.time.Instant;
import java.util.List;

public interface EventService {
    EventResponse create(EventCreateRequest request);
    EventResponse update(String eventId, String requesterArtistId, EventUpdateRequest request);
    void deleteDraft(String eventId, String requesterArtistId);
    EventResponse publish(String eventId, String requesterArtistId);
    EventResponse findById(String eventId);
    List<EventResponse> findLiveAfter(Instant after);
    List<EventResponse> findByArtist(String artistId);
}
