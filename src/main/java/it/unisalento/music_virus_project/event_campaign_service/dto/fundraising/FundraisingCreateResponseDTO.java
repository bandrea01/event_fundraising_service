package it.unisalento.music_virus_project.event_campaign_service.dto.fundraising;

import it.unisalento.music_virus_project.event_campaign_service.domain.enums.FundraisingStatus;

public class FundraisingCreateResponseDTO {
    private String fundraisingId;
    private String fundraisingName;
    private FundraisingStatus status;

    public FundraisingCreateResponseDTO(String fundraisingId, String fundraisingName, FundraisingStatus status) {
        this.fundraisingId = fundraisingId;
        this.fundraisingName = fundraisingName;
        this.status = status;
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

    public FundraisingStatus getStatus() {
        return status;
    }

    public void setStatus(FundraisingStatus status) {
        this.status = status;
    }
}
