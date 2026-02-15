package it.unisalento.music_virus_project.event_fundraising_service.dto.event;

import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.EventStatus;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.VenuePromotionEnum;

import java.time.Instant;

public class EventResponseDTO {

    private String eventId;
    private String fundraisingId;
    private String artistId;
    private String venueId;
    private EventStatus status;
    private VenuePromotionEnum venuePromotion;
    private String eventName;
    private Instant eventDate;

    public EventResponseDTO(String eventId, String fundraisingId, String artistId, String venueId, EventStatus status, String eventName, Instant eventDate, VenuePromotionEnum venuePromotion) {
        this.eventId = eventId;
        this.fundraisingId = fundraisingId;
        this.artistId = artistId;
        this.venueId = venueId;
        this.status = status;
        this.venuePromotion = venuePromotion;
        this.eventName = eventName;
        this.eventDate = eventDate;
    }

    public EventResponseDTO() {
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getFundraisingId() {
        return fundraisingId;
    }

    public void setFundraisingId(String fundraisingId) {
        this.fundraisingId = fundraisingId;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }

    public VenuePromotionEnum getVenuePromotion() {
        return venuePromotion;
    }

    public void setVenuePromotion(VenuePromotionEnum venuePromotion) {
        this.venuePromotion = venuePromotion;
    }
}
