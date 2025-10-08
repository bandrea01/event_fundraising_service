package it.unisalento.music_virus_project.event_campaign_service.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "tiers")
@CompoundIndex(name = "event_minamount_idx", def = "{'eventId': 1, 'minAmount': 1}")
public class Tier {

    @Id
    private String id;
    private String eventId;
    private String label;
    private BigDecimal minAmount;
    private List<String> benefits;

    public Tier() {}

    public Tier(String eventId, String label, BigDecimal minAmount, List<String> benefits) {
        this.eventId = eventId;
        this.label = label;
        this.minAmount = minAmount;
        this.benefits = benefits;
    }

    // Getters e Setters
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
