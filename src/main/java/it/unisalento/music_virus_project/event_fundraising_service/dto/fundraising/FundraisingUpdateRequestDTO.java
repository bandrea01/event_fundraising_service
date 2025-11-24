package it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising;

import java.math.BigDecimal;
import java.time.Instant;

public class FundraisingUpdateRequestDTO {
    private String fundraisingName;
    private String venueId;
    private BigDecimal targetAmount;
    private Instant eventDate;

    public FundraisingUpdateRequestDTO() {
    }

    public String getFundraisingName() {
        return fundraisingName;
    }

    public void setFundraisingName(String fundraisingName) {
        this.fundraisingName = fundraisingName;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }
}
