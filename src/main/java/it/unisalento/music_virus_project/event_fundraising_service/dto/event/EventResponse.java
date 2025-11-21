package it.unisalento.music_virus_project.event_fundraising_service.dto.event;

import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.EventStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class EventResponse {

    private String id;
    private String artistId;
    private String venueId;
    private EventStatus status;
    private String title;
    private String description;
    private Instant eventDate;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private List<String> genres;
    private String city;
    private String country;
    private Instant createdAt;
    private Instant updatedAt;

    public EventResponse() {}

    // Getters/Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getArtistId() { return artistId; }
    public void setArtistId(String artistId) { this.artistId = artistId; }
    public String getVenueId() { return venueId; }
    public void setVenueId(String venueId) { this.venueId = venueId; }
    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Instant getEventDate() { return eventDate; }
    public void setEventDate(Instant eventDate) { this.eventDate = eventDate; }
    public BigDecimal getGoalAmount() { return goalAmount; }
    public void setGoalAmount(BigDecimal goalAmount) { this.goalAmount = goalAmount; }
    public BigDecimal getCurrentAmount() { return currentAmount; }
    public void setCurrentAmount(BigDecimal currentAmount) { this.currentAmount = currentAmount; }
    public List<String> getGenres() { return genres; }
    public void setGenres(List<String> genres) { this.genres = genres; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
