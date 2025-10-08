package it.unisalento.music_virus_project.event_campaign_service.domain.entity;

import it.unisalento.music_virus_project.event_campaign_service.domain.enums.VenueRequestStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "venue_requests")
@CompoundIndex(name = "venue_status_idx", def = "{'venueId': 1, 'status': 1}")
public class VenueRequest {

    @Id
    private String id;
    private String eventId;
    private String venueId;
    private VenueRequestStatus status;
    private BigDecimal coFundingAmount;
    private String benefitDescription;
    private Instant decisionAt;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public VenueRequest() {
        this.status = VenueRequestStatus.PENDING;
    }

    public VenueRequest(String eventId, String venueId) {
        this.eventId = eventId;
        this.venueId = venueId;
        this.status = VenueRequestStatus.PENDING;
    }

    // Getters e Setters
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
