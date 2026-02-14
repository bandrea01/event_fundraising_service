package it.unisalento.music_virus_project.event_fundraising_service.domain.entity;

import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.VenuePromotionEnum;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "fundraisings")
@CompoundIndex(name = "event_minamount_idx", def = "{'eventId': 1, 'minAmount': 1}")
public class Fundraising {

    public static final long EXPIRATION_OFFSET_SECONDS = 86400;

    @Id
    private String fundraisingId;

    @Indexed
    private String artistId;
    @Indexed
    private String venueId;

    private String fundraisingName;
    private BigDecimal currentAmount;
    private BigDecimal targetAmount;
    private VenuePromotionEnum venuePromotion;
    private FundraisingStatus status;
    private Instant eventDate;
    private Instant expirationDate;

    @CreatedDate
    private Instant createdAt;

    public Fundraising(String artistId, String venueId, String fundraisingName, BigDecimal targetAmount, FundraisingStatus status, Instant eventDate) {
        this.artistId = artistId;
        this.venueId = venueId;
        this.fundraisingName = fundraisingName;
        this.currentAmount = BigDecimal.ZERO;
        this.targetAmount = targetAmount;
        this.status = status;
        this.venuePromotion = VenuePromotionEnum.NONE;
        this.eventDate = eventDate;
        this.createdAt = Instant.now();
        this.expirationDate = eventDate.minusSeconds(EXPIRATION_OFFSET_SECONDS);
    }

    public String getFundraisingId() {
        return fundraisingId;
    }

    public void setFundraisingId(String fundraisingId) {
        this.fundraisingId = fundraisingId;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public String getFundraisingName() {
        return fundraisingName;
    }

    public void setFundraisingName(String fundraisingName) {
        this.fundraisingName = fundraisingName;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public FundraisingStatus getStatus() {
        return status;
    }

    public void setStatus(FundraisingStatus status) {
        this.status = status;
    }

    public VenuePromotionEnum getVenuePromotion() {
        return venuePromotion;
    }

    public void setVenuePromotion(VenuePromotionEnum venuePromotion) {
        this.venuePromotion = venuePromotion;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

}
