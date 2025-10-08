package it.unisalento.music_virus_project.event_campaign_service.domain.entity;

import it.unisalento.music_virus_project.event_campaign_service.domain.enums.EventStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(collection = "events")
@CompoundIndex(name = "status_date_idx", def = "{'status': 1, 'eventDate': 1}")
public class Event {

    @Id
    private String id;

    @Indexed
    private String artistId;

    @Indexed
    private String venueId;

    @Indexed
    private EventStatus status;

    private String title;
    private String description;
    private Instant eventDate;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private List<String> genres;
    private String city;
    private String country;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public Event() {}

    public Event(String artistId, String title, String description, Instant eventDate, BigDecimal goalAmount) {
        this.artistId = artistId;
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.goalAmount = goalAmount;
        this.status = EventStatus.DRAFT;
        this.currentAmount = BigDecimal.ZERO;
    }

    // Getters e Setters
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
