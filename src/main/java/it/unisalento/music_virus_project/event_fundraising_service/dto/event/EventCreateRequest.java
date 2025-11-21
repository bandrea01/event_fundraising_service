package it.unisalento.music_virus_project.event_fundraising_service.dto.event;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public class EventCreateRequest {

    @NotBlank
    private String artistId;

    @NotBlank
    @Size(max = 120)
    private String title;

    @NotBlank
    @Size(max = 2000)
    private String description;

    @NotNull
    private Instant eventDate; // UTC

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal goalAmount;

    private List<@Size(max = 40) String> genres;

    @Size(max = 80)
    private String city;

    @Size(max = 80)
    private String country;

    public EventCreateRequest() {}

    // Getters/Setters
    public String getArtistId() { return artistId; }
    public void setArtistId(String artistId) { this.artistId = artistId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Instant getEventDate() { return eventDate; }
    public void setEventDate(Instant eventDate) { this.eventDate = eventDate; }
    public BigDecimal getGoalAmount() { return goalAmount; }
    public void setGoalAmount(BigDecimal goalAmount) { this.goalAmount = goalAmount; }
    public List<String> getGenres() { return genres; }
    public void setGenres(List<String> genres) { this.genres = genres; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
}
