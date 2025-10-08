package it.unisalento.music_virus_project.event_campaign_service.dto.tier;

import java.math.BigDecimal;
import java.util.List;

public class TierResponse {

    private String id;
    private String eventId;
    private String label;
    private BigDecimal minAmount;
    private List<String> benefits;

    public TierResponse() {}

    // Getters/Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public BigDecimal getMinAmount() { return minAmount; }
    public void setMinAmount(BigDecimal minAmount) { this.minAmount = minAmount; }
    public List<String> getBenefits() { return benefits; }
    public void setBenefits(List<String> benefits) { this.benefits = benefits; }
}
