package it.unisalento.music_virus_project.event_fundraising_service.messaging.dto;

import java.math.BigDecimal;

public class EventCreationDTO {
    private String artistId;
    private String eventId;
    private String fundraisingId;
    private BigDecimal amount;

    public String getArtistId() {
        return artistId;
    }
    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }
    public String getEventId() {
        return eventId;
    }
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public String getFundraisingId() {
        return fundraisingId;
    }
    public void setFundraisingId(String fundraisingId) {
        this.fundraisingId = fundraisingId;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
