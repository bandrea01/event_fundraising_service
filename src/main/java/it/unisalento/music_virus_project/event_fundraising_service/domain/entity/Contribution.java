package it.unisalento.music_virus_project.event_fundraising_service.domain.entity;

import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.ContributionStatus;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.ContributionVisibility;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.math.BigDecimal;
import java.time.Instant;

public class Contribution {

    @Id
    private String contributionId;

    @Indexed
    private String fundraisingId;
    @Indexed
    private String userId;

    private BigDecimal amount;
    private ContributionVisibility contributionVisibility;
    private ContributionStatus status;

    @CreatedDate
    private Instant createdAt;

    public Contribution(String eventId, String userId, BigDecimal amount, ContributionVisibility contributionVisibility, ContributionStatus status) {
        this.fundraisingId = eventId;
        this.userId = userId;
        this.amount = amount;
        this.contributionVisibility = contributionVisibility;
        this.status = status;
        this.createdAt = Instant.now();
    }

    public String getContributionId() {
        return contributionId;
    }

    public void setContributionId(String contributionId) {
        this.contributionId = contributionId;
    }

    public String getFundraisingId() {
        return fundraisingId;
    }

    public void setFundraisingId(String fundraisingId) {
        this.fundraisingId = fundraisingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public ContributionVisibility getVisibility() {
        return contributionVisibility;
    }

    public void setVisibility(ContributionVisibility contributionVisibility) {
        this.contributionVisibility = contributionVisibility;
    }

    public ContributionStatus getStatus() {
        return status;
    }

    public void setStatus(ContributionStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}