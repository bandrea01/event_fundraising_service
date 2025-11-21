package it.unisalento.music_virus_project.event_fundraising_service.domain.entity;

import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.EventStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Instant;

public class Event {

    @Id
    private String eventId;

    @Indexed
    private String fundraisingId;
    @Indexed
    private String artistId;
    @Indexed
    private String venueId;

    private String eventName;
    private Instant eventDate;
    private EventStatus status;

    @CreatedDate
    private Instant createdAt;

    public Event(Fundraising fundraising) {
        this.fundraisingId = fundraising.getFundraisingId();
        this.eventName = fundraising.getFundraisingName();
        this.eventDate = fundraising.getEventDate();
        this.artistId = fundraising.getArtistId();
        this.venueId = fundraising.getVenueId();
        this.status = EventStatus.CONFIRMED;
        this.createdAt = Instant.now();
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getFundraisingId() {
        return fundraisingId;
    }

    public void setFundraisingId(String fundraisingId) {
        this.fundraisingId = fundraisingId;
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

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
