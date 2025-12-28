package it.unisalento.music_virus_project.event_fundraising_service.dto.event;

public class EventVenueCounterResponseDTO {
    private String venueId;
    private Integer eventCounter;

    public EventVenueCounterResponseDTO() {
    }

    public EventVenueCounterResponseDTO(String venueId, Integer eventCounter) {
        this.venueId = venueId;
        this.eventCounter = eventCounter;
    }

    public String getVenueId() {
        return venueId;
    }
    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }
    public Integer getEventCounter() {
        return eventCounter;
    }
    public void setEventCounter(Integer eventCounter) {
        this.eventCounter = eventCounter;
    }
}
