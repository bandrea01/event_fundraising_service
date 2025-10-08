package it.unisalento.music_virus_project.event_campaign_service.dto.venue;

import jakarta.validation.constraints.*;

public class VenueRequestCreateRequest {

    @NotBlank
    private String eventId;

    @NotBlank
    private String venueId;

    public VenueRequestCreateRequest() {}

    // Getters/Setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getVenueId() { return venueId; }
    public void setVenueId(String venueId) { this.venueId = venueId; }
}
