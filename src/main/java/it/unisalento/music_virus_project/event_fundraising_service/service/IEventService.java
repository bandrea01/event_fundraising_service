package it.unisalento.music_virus_project.event_fundraising_service.service;

import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Fundraising;
import it.unisalento.music_virus_project.event_fundraising_service.domain.entity.Role;
import it.unisalento.music_virus_project.event_fundraising_service.dto.event.*;

import java.time.Instant;

public interface IEventService {
    EventListResponseDTO getAllEvents();
    EventResponseDTO getEventById(String eventId);
    EventResponseDTO getEventByFundraisingId(String fundraisingId);
    EventListResponseDTO getEventsByArtistId(String artistId);
    EventListResponseDTO getEventsByVenueId(String venueId);
    EventListResponseDTO getEventsByStatus(String status);
    EventListResponseDTO getEventsByDateRange(Instant startDate, Instant endDate);
    EventListResponseDTO getEventsByDate(Instant date);
    EventVenueCounterListResponseDTO getEventVenueCounter();
    EventResponseDTO updateEvent(String eventId, EventUpdateRequestDTO updateRequest);
    EventResponseDTO createEventFromFundraising(Fundraising fundraising);
    EventResponseDTO cancelEventById(String eventId);
    EventListResponseDTO disableArtistEvents(String artistId);
    EventListResponseDTO disableVenueEvents(String venueId);
    EventListResponseDTO disableEventsByUserId(String userId, Role role);
    FeedbackListResponseDTO getEventFeedbacks(String eventId);
    FeedbackResponseDTO addEventFeedback(String eventId, FeedbackCreateRequestDTO feedbackRequest);
}
