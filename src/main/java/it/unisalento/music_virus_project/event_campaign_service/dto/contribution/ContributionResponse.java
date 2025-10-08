package it.unisalento.music_virus_project.event_campaign_service.dto.contribution;

import it.unisalento.music_virus_project.event_campaign_service.domain.enums.ContributionStatus;
import it.unisalento.music_virus_project.event_campaign_service.domain.enums.Visibility;

import java.math.BigDecimal;
import java.time.Instant;

public class ContributionResponse {

    private String id;
    private String eventId;
    private String fanId;
    private BigDecimal amount;
    private Visibility visibility;
    private ContributionStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public ContributionResponse() {}

    // Getters/Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getFanId() { return fanId; }
    public void setFanId(String fanId) { this.fanId = fanId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public Visibility getVisibility() { return visibility; }
    public void setVisibility(Visibility visibility) { this.visibility = visibility; }
    public ContributionStatus getStatus() { return status; }
    public void setStatus(ContributionStatus status) { this.status = status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
