package it.unisalento.music_virus_project.event_fundraising_service.dto.event;

import java.util.List;

public class EventListResponseDTO {
    private List<EventResponseDTO> events;

    public EventListResponseDTO(List<EventResponseDTO> events) {
        this.events = events;
    }

    public EventListResponseDTO() {}

    public List<EventResponseDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventResponseDTO> events) {
        this.events = events;
    }

    public void addEvent(EventResponseDTO event) {
        this.events.add(event);
    }
}
