package it.unisalento.music_virus_project.event_fundraising_service.dto.event;

import java.util.ArrayList;
import java.util.List;

public class EventVenueCounterListResponseDTO {

    private List<EventVenueCounterResponseDTO> eventVenueCounters;

    public EventVenueCounterListResponseDTO() {
        this.eventVenueCounters = new ArrayList<>();
    }

    public EventVenueCounterListResponseDTO(List<EventVenueCounterResponseDTO> eventVenueCounters) {
        this.eventVenueCounters = eventVenueCounters;
    }

    public List<EventVenueCounterResponseDTO> getEventVenueCounters() {
        return eventVenueCounters;
    }
    public void setEventVenueCounters(List<EventVenueCounterResponseDTO> eventVenueCounters) {
        this.eventVenueCounters = eventVenueCounters;
    }

}
