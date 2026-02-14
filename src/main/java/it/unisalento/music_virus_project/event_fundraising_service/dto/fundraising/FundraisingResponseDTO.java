package it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising;

import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.VenuePromotionEnum;

import java.math.BigDecimal;
import java.time.Instant;

public class FundraisingResponseDTO {
    private String fundraisingId;
    private String fundraisingName;
    private String artistId;
    private String venueId;
    private BigDecimal currentAmount;
    private BigDecimal targetAmount;
    private FundraisingStatus status;
    private VenuePromotionEnum venuePromotion;
    private Instant eventDate;
    private Instant expirationDate;

    public FundraisingResponseDTO(String fundraisingId, String fundraisingName, String artistId, String venueId, BigDecimal currentAmount, BigDecimal targetAmount, FundraisingStatus status, VenuePromotionEnum venuePromotion, Instant eventDate, Instant expirationDate) {
        this.fundraisingId = fundraisingId;
        this.fundraisingName = fundraisingName;
        this.artistId = artistId;
        this.venueId = venueId;
        this.currentAmount = currentAmount;
        this.targetAmount = targetAmount;
        this.status = status;
        this.venuePromotion = venuePromotion;
        this.eventDate = eventDate;
        this.expirationDate = expirationDate;
    }

    public FundraisingResponseDTO() {
    }

    public String getFundraisingId() {
        return fundraisingId;
    }

    public void setFundraisingId(String fundraisingId) {
        this.fundraisingId = fundraisingId;
    }

    public String getFundraisingName() {
        return fundraisingName;
    }

    public void setFundraisingName(String fundraisingName) {
        this.fundraisingName = fundraisingName;
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

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }
}
