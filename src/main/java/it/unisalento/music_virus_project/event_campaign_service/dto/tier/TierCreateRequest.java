package it.unisalento.music_virus_project.event_campaign_service.dto.tier;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class TierCreateRequest {

    @NotBlank
    private String eventId;

    @NotBlank
    @Size(max = 60)
    private String label;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal minAmount;

    private List<@Size(max = 120) String> benefits;

    public TierCreateRequest() {}

    // Getters/Setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public BigDecimal getMinAmount() { return minAmount; }
    public void setMinAmount(BigDecimal minAmount) { this.minAmount = minAmount; }
    public List<String> getBenefits() { return benefits; }
    public void setBenefits(List<String> benefits) { this.benefits = benefits; }
}
