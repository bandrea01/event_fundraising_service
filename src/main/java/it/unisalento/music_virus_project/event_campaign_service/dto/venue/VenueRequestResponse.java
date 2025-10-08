package it.unisalento.music_virus_project.event_campaign_service.dto.venue;

import it.unisalento.music_virus_project.event_campaign_service.domain.enums.VenueRequestStatus;

import java.math.BigDecimal;
import java.time.Instant;

public class VenueRequestResponse {

    private String id;
    private String eventId;
    private String venueId;
    private VenueRequestStatus status;
    private BigDecimal coFundingAmount;
    private String benefitDescription;
    private Instant decisionAt;
    private Instant createdAt;
    private Instant updatedAt;

    public VenueRequestResponse() {}

    // Getters/Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getVenueId() { return venueId; }
    public void setVenueId(String venueId) { this.venueId = venueId; }
    public VenueRequestStatus getStatus() { return status; }
    public void setStatus(VenueRequestStatus status) { this.status = status; }
    public BigDecimal getCoFundingAmount() { return coFundingAmount; }
    public void setCoFundingAmount(BigDecimal coFundingAmount) { this.coFundingAmount = coFundingAmount; }
    public String getBenefitDescription() { return benefitDescription; }
    public void setBenefitDescription(String benefitDescription) { this.benefitDescription = benefitDescription; }
    public Instant getDecisionAt() { return decisionAt; }
    public void setDecisionAt(Instant decisionAt) { this.decisionAt = decisionAt; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
