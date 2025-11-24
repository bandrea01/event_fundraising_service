package it.unisalento.music_virus_project.event_fundraising_service.dto.contribution;

import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.ContributionVisibility;

import java.math.BigDecimal;

public class ContributionCreateRequest {

    private String fundraisingId;
    private String userId;
    private BigDecimal amount;
    private ContributionVisibility visibility;

    public ContributionCreateRequest(String fundraisingId, String userId, BigDecimal amount, ContributionVisibility visibility) {
        this.fundraisingId = fundraisingId;
        this.userId = userId;
        this.amount = amount;
        this.visibility = visibility;
    }

    public ContributionCreateRequest() {}

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
        return visibility;
    }

    public void setVisibility(ContributionVisibility visibility) {
        this.visibility = visibility;
    }
}
