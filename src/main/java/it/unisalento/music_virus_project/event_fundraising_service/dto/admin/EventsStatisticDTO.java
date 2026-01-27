package it.unisalento.music_virus_project.event_fundraising_service.dto.admin;

import java.util.ArrayList;
import java.util.List;

public class EventsStatisticDTO {
    private List<GenericCounterDTO> counters;

    public EventsStatisticDTO() {
        this.counters = new ArrayList<>();
    }

    public List<GenericCounterDTO> getCounters() {
        return counters;
    }

    public void setCounters(List<GenericCounterDTO> counters) {
        this.counters = counters;
    }

    public void addCounter(GenericCounterDTO counter) {
        this.counters.add(counter);
    }

}


