package it.unisalento.music_virus_project.event_campaign_service.dto.fundraising;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.Instant;

public class FundraisingCreateRequestDTO {

    @NotBlank
    private String artistId;
    @NotBlank
    private String venueId;
    @NotBlank
    private Instant eventDate;
    @NotBlank
    private String fundraisingName;
    @NotNull
    @DecimalMin(value = "0.00", inclusive = false)
    private BigDecimal targetAmount;

    public FundraisingCreateRequestDTO() {}

    public @NotBlank String getArtistId() {
        return artistId;
    }

    public void setArtistId(@NotBlank String artistId) {
        this.artistId = artistId;
    }

    public @NotBlank String getVenueId() {
        return venueId;
    }

    public void setVenueId(@NotBlank String venueId) {
        this.venueId = venueId;
    }

    public @NotBlank Instant getEventDate() {
        return eventDate;
    }

    public void setEventDate(@NotBlank Instant eventDate) {
        this.eventDate = eventDate;
    }

    public @NotBlank String getFundraisingName() {
        return fundraisingName;
    }

    public void setFundraisingName(@NotBlank String fundraisingName) {
        this.fundraisingName = fundraisingName;
    }

    public @NotNull @DecimalMin(value = "0.00", inclusive = false) BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(@NotNull @DecimalMin(value = "0.00", inclusive = false) BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }
}
