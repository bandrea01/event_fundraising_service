package it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising;

import java.math.BigDecimal;
import java.time.Instant;

public class FundraisingCreateRequestDTO {

    private String artistId;
    private String artist;
    private String venueName;
    private String venueId;
    private Instant eventDate;
    private String fundraisingName;
    private BigDecimal targetAmount;

    public FundraisingCreateRequestDTO() {
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }

    public String getFundraisingName() {
        return fundraisingName;
    }

    public void setFundraisingName(String fundraisingName) {
        this.fundraisingName = fundraisingName;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }
}
