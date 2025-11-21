package it.unisalento.music_virus_project.event_fundraising_service.domain.entity;

import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.FundraisingStatus;
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

    @Id
    private String fundraisingId;

    @Indexed
    private String artistId;
    @Indexed
    private String venueId;

    private String fundraisingName;
    private BigDecimal targetAmount;
    private FundraisingStatus status;
    private Instant eventDate;

    @CreatedDate
    private Instant createdAt;

    public Fundraising(String artistId, String venueId, String fundraisingName, BigDecimal targetAmount, FundraisingStatus status, Instant eventDate) {
        this.artistId = artistId;
        this.venueId = venueId;
        this.fundraisingName = fundraisingName;
        this.targetAmount = targetAmount;
        this.status = status;
        this.eventDate = eventDate;
        this.createdAt = Instant.now();
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

    //    public Status calculateStatus(Date now) {
//        double totalContribution = this.contribution.getTotalContribution();
//        boolean achieved = totalContribution >= targetAmount;
//
//        Date fundraisingEndDate = new Date(this.eventDate.getTime() - 24L * 60 * 60 * 1000); // 24 ore prima
//
//        if (this.status != Status.NOT_ACHIEVED) {
//            return this.status;
//        }
//
//        if (now.before(fundraisingEndDate)) {
//            return achieved ? Status.ACHIEVED : Status.ACTIVE;
//        } else {
//            return achieved ? Status.ACHIEVED : Status.NOT_ACHIEVED;
//        }
//    }
//
//    public Status refreshStatus(Date now) {
//        this.status = calculateStatus(now);
//        return this.status;
//    }

}
