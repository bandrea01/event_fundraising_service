package it.unisalento.music_virus_project.event_fundraising_service.dto.event;

import java.time.Instant;

public class EventUpdateRequestDTO {

    private String eventName;
    private Instant eventDate;

    public EventUpdateRequestDTO() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Instant getEventDate() {
        return eventDate;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }
}
