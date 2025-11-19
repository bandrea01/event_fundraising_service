package it.unisalento.music_virus_project.event_campaign_service.dto.contribution;

import it.unisalento.music_virus_project.event_campaign_service.domain.enums.ContributionVisibility;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ContributionCreateRequest {

    @NotBlank
    private String eventId;

    @NotBlank
    private String fanId;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal amount;

    @NotNull
    private ContributionVisibility contributionVisibility; // PUBLIC o ANONYMOUS

    public ContributionCreateRequest() {}

    // Getters/Setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getFanId() { return fanId; }
    public void setFanId(String fanId) { this.fanId = fanId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public ContributionVisibility getVisibility() { return contributionVisibility; }
    public void setVisibility(ContributionVisibility contributionVisibility) { this.contributionVisibility = contributionVisibility; }
}
