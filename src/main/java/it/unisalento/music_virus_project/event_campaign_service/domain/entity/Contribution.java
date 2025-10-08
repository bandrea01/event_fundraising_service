package it.unisalento.music_virus_project.event_campaign_service.domain.entity;

import it.unisalento.music_virus_project.event_campaign_service.domain.enums.ContributionStatus;
import it.unisalento.music_virus_project.event_campaign_service.domain.enums.Visibility;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "contributions")
@CompoundIndex(name = "event_fan_idx", def = "{'eventId': 1, 'fanId': 1}")
public class Contribution {

    @Id
    private String id;

    @Indexed
    private String eventId;

    @Indexed
    private String fanId;

    private BigDecimal amount;
    private Visibility visibility;
    private ContributionStatus status;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public Contribution() {}

    public Contribution(String eventId, String fanId, BigDecimal amount, Visibility visibility) {
        this.eventId = eventId;
        this.fanId = fanId;
        this.amount = amount;
        this.visibility = visibility;
        this.status = ContributionStatus.AUTHORIZED;
    }

    // Getters e Setters
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
