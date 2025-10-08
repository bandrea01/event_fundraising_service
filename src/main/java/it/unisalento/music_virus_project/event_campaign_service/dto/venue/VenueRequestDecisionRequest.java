package it.unisalento.music_virus_project.event_campaign_service.dto.venue;

import it.unisalento.music_virus_project.event_campaign_service.domain.enums.VenueRequestStatus;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class VenueRequestDecisionRequest {

    @NotNull
    private VenueRequestStatus status; // ACCEPTED o REJECTED

    @DecimalMin(value = "0.00")
    private BigDecimal coFundingAmount; // opzionale

    @Size(max = 200)
    private String benefitDescription;  // opzionale

    public VenueRequestDecisionRequest() {}

    // Getters/Setters
    public VenueRequestStatus getStatus() { return status; }
    public void setStatus(VenueRequestStatus status) { this.status = status; }
    public BigDecimal getCoFundingAmount() { return coFundingAmount; }
    public void setCoFundingAmount(BigDecimal coFundingAmount) { this.coFundingAmount = coFundingAmount; }
    public String getBenefitDescription() { return benefitDescription; }
    public void setBenefitDescription(String benefitDescription) { this.benefitDescription = benefitDescription; }
}
