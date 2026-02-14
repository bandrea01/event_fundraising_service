package it.unisalento.music_virus_project.event_fundraising_service.dto.fundraising;

import it.unisalento.music_virus_project.event_fundraising_service.domain.enums.VenuePromotionEnum;

public class VenuePromotionRequestDTO {
    private VenuePromotionEnum promotion;

    public VenuePromotionRequestDTO() {
    }

    public VenuePromotionEnum getPromotion() {
        return promotion;
    }

    public void setPromotion(VenuePromotionEnum promotion) {
        this.promotion = promotion;
    }
}
